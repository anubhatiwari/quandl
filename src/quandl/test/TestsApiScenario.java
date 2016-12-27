package quandl.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import quandl.utils.*;

public class TestsApiScenario extends PreTestSetupImpl implements AbstractFunctions {

	private final ScenarioCacheManager scenarioCacheManager = ScenarioCacheManager
			.getInstance();
	final Utils Utils = new Utils();
	TestImplementation testImpl = null;
	

	
	@Override
	@Before
	
	public void setEnvironment() throws Exception {
		scenarioCacheManager.clear();
		final String baseUrl = "baseUrl"; final String apiVersion="apiVersion"; final String databaseApi="databaseApi"; final String datasetApi="datasetApi"; final String datatableApi="datatableApi";
		final String returnFormat = "returnFormat"; final String statusCodeDB="statusCodeDB"; final String errorCodeDB="errorCodeDB", metaDataDB = "metaDataDB",datasetFB="datasetFB",statusCodeDS="statusCodeDS";
		final String errorCodeDS="errorCodeDS", errorCodeDT="errorCodeDT",statusCodeDT="statusCodeDT",datatable="datatable",apiKey="apiKey"; 
		
		
		final String baseQuandlUrl = Utils.getValue(Constants.INPUT_DATA_FILE, baseUrl);
		scenarioCacheManager.store(Cache.BASE_URL, baseQuandlUrl);
		
		final String apiVer = Utils.getValue(Constants.INPUT_DATA_FILE, apiVersion);
		scenarioCacheManager.store(Cache.API_VERSION, apiVer);
		
		final String databaseApiReq = Utils.getValue(Constants.INPUT_DATA_FILE,databaseApi );
		scenarioCacheManager.store(Cache.DATABASE_API, databaseApiReq);
		
		final String datasetApiReq = Utils.getValue(Constants.INPUT_DATA_FILE,datasetApi );
		scenarioCacheManager.store(Cache.DATASET_API, datasetApiReq);
		
		final String datatableApiReq = Utils.getValue(Constants.INPUT_DATA_FILE,datatableApi );
		scenarioCacheManager.store(Cache.DATATABLE_API, datatableApiReq);
		

		final String dataReturnFormat = Utils.getValue(Constants.INPUT_DATA_FILE,returnFormat );
		scenarioCacheManager.store(Cache.RETURN_FORMAT, dataReturnFormat);
		
		final String returnStatusCodeDB = Utils.getValue(Constants.INPUT_DATA_FILE,statusCodeDB );
		scenarioCacheManager.store(Cache.DB_STATUSCODE, returnStatusCodeDB);
		
		final String returnErrorCodeDB = Utils.getValue(Constants.INPUT_DATA_FILE,errorCodeDB );
		scenarioCacheManager.store(Cache.DB_ERRORCODE, returnErrorCodeDB);
		
		final String returnMetaDataDB = Utils.getValue(Constants.INPUT_DATA_FILE,metaDataDB );
		scenarioCacheManager.store(Cache.DB_METADATA, returnMetaDataDB);
		
		final String returnDatasetFB = Utils.getValue(Constants.INPUT_DATA_FILE,datasetFB );
		scenarioCacheManager.store(Cache.DS_FB, returnDatasetFB);
		
		final String returnStatusCodeDS = Utils.getValue(Constants.INPUT_DATA_FILE,statusCodeDS );
		scenarioCacheManager.store(Cache.DS_STATUSCODE, returnStatusCodeDS);
		
		final String returnErrorCodeDS = Utils.getValue(Constants.INPUT_DATA_FILE,errorCodeDS );
		scenarioCacheManager.store(Cache.DS_ERRORCODE, returnErrorCodeDS);
		
		
		final String returnDatatable = Utils.getValue(Constants.INPUT_DATA_FILE,datatable );
		scenarioCacheManager.store(Cache.DATABLE, returnDatatable);
		
		final String returnStatusCodeDT = Utils.getValue(Constants.INPUT_DATA_FILE,statusCodeDT );
		scenarioCacheManager.store(Cache.DT_STATUSCODE, returnStatusCodeDT);
		
		final String returnErrorCodeDT = Utils.getValue(Constants.INPUT_DATA_FILE,errorCodeDT );
		scenarioCacheManager.store(Cache.DT_ERRORCODE, returnErrorCodeDT);
		
		final String returnAPIKey = Utils.getValue(Constants.INPUT_DATA_FILE,apiKey );
		scenarioCacheManager.store(Cache.DT_APIKEY, returnAPIKey);
		
		
	}

	// our database API returns a list of databases	 
	@Test
	public void GetDatabases() throws Exception {
		
		TestImplementation testImpl = new TestImplementation();
		final Constants cont = new Constants();
		cont.setUrl(scenarioCacheManager.retrieveAsString(Cache.BASE_URL));
		cont.setVersion(scenarioCacheManager.retrieveAsString(Cache.API_VERSION));
		cont.setDatabase(scenarioCacheManager.retrieveAsString(Cache.DATABASE_API));
		cont.setReturnFormat(scenarioCacheManager.retrieveAsString(Cache.RETURN_FORMAT));
		cont.setStatusCodeDB(scenarioCacheManager.retrieveAsString(Cache.DB_STATUSCODE));
		cont.setErrorCodeDB(scenarioCacheManager.retrieveAsString(Cache.DB_ERRORCODE));
		cont.setMetaDataDB(scenarioCacheManager.retrieveAsString(Cache.DB_METADATA));
		
		
		testImpl.submitRequest("GETDATABASE", cont);
		
		testImpl.validate(cont.getErrorCodeDB(), cont.getStatusCodeDB(),"GETDATABASE");
		
	}

	
	// our dataset API returns data for the `WIKI/FB` dataset 
	@Test
	public void GetDataSet() throws Exception {

		TestImplementation testImpl = new TestImplementation();
		final Constants cont = new Constants();

		cont.setUrl(scenarioCacheManager.retrieveAsString(Cache.BASE_URL));
		cont.setVersion(scenarioCacheManager.retrieveAsString(Cache.API_VERSION));
		cont.setDataset(scenarioCacheManager.retrieveAsString(Cache.DATASET_API));
		cont.setDatasetFB(scenarioCacheManager.retrieveAsString(Cache.DS_FB));
		cont.setStatusCodeDS(scenarioCacheManager.retrieveAsString(Cache.DS_STATUSCODE));
		cont.setErrorCodeDS(scenarioCacheManager.retrieveAsString(Cache.DS_ERRORCODE));
		cont.setReturnFormat(scenarioCacheManager.retrieveAsString(Cache.RETURN_FORMAT));


		testImpl.submitRequest("GETDATASET", cont);

		testImpl.validate(cont.getErrorCodeDS(), cont.getStatusCodeDS(),"GETDATASET");

	}	


	// our datatable API returns data for the `WIKI` with a valid API key
	@Test
	public void GetDataTable() throws Exception {

		TestImplementation testImpl = new TestImplementation();
		final Constants cont = new Constants();

		cont.setUrl(scenarioCacheManager.retrieveAsString(Cache.BASE_URL));
		cont.setVersion(scenarioCacheManager.retrieveAsString(Cache.API_VERSION));
		cont.setDatatableAPI(scenarioCacheManager.retrieveAsString(Cache.DATATABLE_API));
		cont.setDatatable(scenarioCacheManager.retrieveAsString(Cache.DATABLE));
		cont.setStatusCodeDT(scenarioCacheManager.retrieveAsString(Cache.DT_STATUSCODE));
		cont.setErrorCodeDT(scenarioCacheManager.retrieveAsString(Cache.DT_ERRORCODE));
		cont.setReturnFormat(scenarioCacheManager.retrieveAsString(Cache.RETURN_FORMAT));
		cont.setApiKey(scenarioCacheManager.retrieveAsString(Cache.DT_APIKEY));

		testImpl.submitRequest("GETDATATABLE", cont);

		testImpl.validate(cont.getErrorCodeDT(), cont.getStatusCodeDT(),"GETDATATABLE");

	}	



	@After
	public void clean() throws Exception {

		// no implementation 
	}

	@Override
	public void login(String baseUrl, String userName, String password, String browser) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setup(String browser) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
