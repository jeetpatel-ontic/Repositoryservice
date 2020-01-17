package components;

import common.UnifiedQueryType;
import services.RepositoryServiceImpl;
import services.UserRepositoryService;
import utilities.MongoStudentRepoUtility;
import utilities.MongoUserRepoUtility;

public class Lookup {

    public static void main(String[] args) {
        UserRepositoryService userMongoUtil = new MongoUserRepoUtility("myDB", "myCollection");
        UnifiedQueryType sampleQuery = new UnifiedQueryType.QueryBuilder().addQueryParamPair("name", "Jeet").addQueryParamPair("age", "20").build();
        userMongoUtil.upsertItem(sampleQuery);
        userMongoUtil.showItems();

        RepositoryServiceImpl studentMongoUtil = new MongoStudentRepoUtility("myStudent", "another");
        UnifiedQueryType anotherQuery = new UnifiedQueryType.QueryBuilder().addQueryParamPair("name", "Jeet").addQueryParamPair("marks", "1").build();
        studentMongoUtil.upsertItem(anotherQuery);
        studentMongoUtil.showItems();

        anotherQuery = new UnifiedQueryType.QueryBuilder().addQueryParamPair("name", "Patel").addQueryParamPair("marks", "2").build();
        studentMongoUtil.upsertItem(anotherQuery);
        studentMongoUtil.showItems();

        System.out.println("Removing...");
        studentMongoUtil.removeItem(anotherQuery);
        studentMongoUtil.showItems();

        anotherQuery = new UnifiedQueryType.QueryBuilder().addQueryParamPair("name", "Jeet").addQueryParamPair("marks", "11").build();
        System.out.println("Updating...");
        studentMongoUtil.upsertItem(anotherQuery);
        studentMongoUtil.showItems();
    }

}
