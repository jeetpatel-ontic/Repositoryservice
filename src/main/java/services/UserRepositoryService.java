package services;

import common.UnifiedQueryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UserRepositoryService implements RepositoryService {
    private static Logger LOGGER = LoggerFactory.getLogger(UserRepositoryService.class);

    protected void fetchUserLog(UnifiedQueryType query, boolean success) {
        LOGGER.debug("User with parameters, {\n" + query.toString() + "}" + ((success) ? ("has been fetched successfully.") : ("cannot be fetched.")));
    }

    protected void upsertUserLog(UnifiedQueryType query, boolean success) {
        LOGGER.debug("User with parameters, {\n" + query.toString() + "}" + ((success) ? ("has been updated successfully.") : ("cannot be updated.")));
    }

    protected void isPresentLog(UnifiedQueryType query, boolean success) {
        LOGGER.debug("User with parameters, {\n" + query.toString() + "}" + ((success) ? ("is present.") : ("is not present.")));
    }

    protected void deleteLog(UnifiedQueryType query, boolean success) {
        LOGGER.debug("User with parameters, {\n" + query.toString() + "}" + ((success) ? ("has been deleted.") : ("cannot be deleted.")));
    }
}
