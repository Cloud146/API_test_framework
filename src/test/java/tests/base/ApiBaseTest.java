package tests.base;

import Data_lib.EnvDataHelper;
import Data_lib.TestDataHelper;
import Requests_lib.*;
import com.fasterxml.jackson.databind.JsonNode;

public class ApiBaseTest {

    protected final GetRequest getRequest = new GetRequest();
    protected final PostRequest postRequest = new PostRequest();
    protected final PutRequest putRequest = new PutRequest();
    protected final DeleteRequest deleteRequest = new DeleteRequest();
    protected final PatchRequest patchRequest = new PatchRequest();

    protected JsonNode testData() {
        return TestDataHelper.getCurrentTestData();
    }

    protected JsonNode env() {
        return EnvDataHelper.get();
    }
}
