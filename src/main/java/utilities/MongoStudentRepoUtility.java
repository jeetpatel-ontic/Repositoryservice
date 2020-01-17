package utilities;

import com.mongodb.*;
import common.UnifiedQueryType;
import services.RepositoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MongoStudentRepoUtility extends RepositoryServiceImpl {

    private MongoClient mongoClient;
    private DB currentDB;
    private DBCollection currentColection;

    public MongoStudentRepoUtility(String database, String collection) {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.currentDB = this.mongoClient.getDB(database);
        this.currentColection = this.currentDB.getCollection(collection);
    }

    public String getCurrentDB() {
        return currentDB.toString();
    }

    public void setCurrentDB(DB currentDB) {
        this.currentDB = currentDB;
    }

    public String getCurrentColection() {
        return currentColection.toString();
    }

    public void setCurrentColection(DBCollection currentColection) {
        this.currentColection = currentColection;
    }

    @Override
    public List<Object> fetchItem(Object query) {
        BasicDBObject queryDBObject = convertQueryObjToDBObj((UnifiedQueryType) query);
        DBCursor cursor = this.currentColection.find(queryDBObject);
        List<Object> response = null;
        if (cursor.hasNext()) {
            response = new ArrayList<Object>();
        }
        while (cursor.hasNext()) {
            response.add(cursor.next());
        }
        if (Objects.nonNull(response)) {
            super.fetchUserLog((UnifiedQueryType) query, true);
        } else {
            super.fetchUserLog((UnifiedQueryType) query, false);
        }
        return response;
    }

    @Override
    public boolean upsertItem(Object query) {
        BasicDBObject queryDBObject = convertQueryObjToDBObj((UnifiedQueryType) query);
        if (this.isPresent(query)) {

            //Updation: Yet to finalize the flow
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", queryDBObject);
            this.currentColection.update(queryDBObject, updateObject);
        } else {
            //Creation
            this.currentColection.insert(queryDBObject);
        }
        super.upsertUserLog((UnifiedQueryType) query, true);//try-catch?
        return true;
    }

    @Override
    public boolean isPresent(Object query) {
        BasicDBObject queryDBObject = convertQueryObjToDBObj((UnifiedQueryType) query);
        DBCursor cursor = this.currentColection.find(queryDBObject);
        if (cursor.hasNext()) {
            super.isPresentLog((UnifiedQueryType) query, true);
            return true;
        } else {
            super.isPresentLog((UnifiedQueryType) query, false);
            return false;
        }
    }

    @Override
    public boolean removeItem(Object query) {
        BasicDBObject queryDBObject = convertQueryObjToDBObj((UnifiedQueryType) query);
        try {
            this.currentColection.remove(queryDBObject);
        } catch (MongoException e) {
            super.deleteLog((UnifiedQueryType) query, false);
            return false;
        }
        super.deleteLog((UnifiedQueryType) query, true);
        return true;
    }

    @Override
    public void showItems() {
        BasicDBObject dummyDBObject = new BasicDBObject();
        DBCursor cursor = this.currentColection.find(dummyDBObject);

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private BasicDBObject convertQueryObjToDBObj(UnifiedQueryType query) {
        BasicDBObject basicDBObject = new BasicDBObject();

        //Appending every query param from map based representation to mongo equivalent representation.
        for (Map.Entry<String, Object> entry : query.getParams().entrySet()) {
            basicDBObject.append(entry.getKey(), entry.getValue());
        }

        return basicDBObject;
    }
}
