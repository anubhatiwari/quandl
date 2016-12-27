package quandl.test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import org.springframework.util.StringUtils;
import com.jayway.restassured.response.Response;
import quandl.utils.Constants;

public class TestImplementation {
	
	 static Response response = null;
	   
	   
	    String errorCode = null, statusCode = null, perPage= null, body =null;

	

	public Response submitRequest(final String submitType, final Object... resources)
            throws Exception
    {

        switch (submitType)
        {
            case "GETDATABASE":
                final Response getResponse = submitGETRequest(resources);
                String body = getResponse.body().asString();
                statusCode = Integer.toString(getResponse.getStatusCode());
                		
                if (StringUtils.hasText(body))
                {
                    errorCode = getResponse.jsonPath().getString("code") == null ? "" : getResponse.jsonPath().getString(
                            "code");
                }
                else
                {
                    errorCode = "";
                }

                break;
                
            case "GETDATASET":
                final Response getsetResponse = submitGETSETRequest(resources);
                String bodyre = getsetResponse.body().asString();
                statusCode = Integer.toString(getsetResponse.getStatusCode());
                		
                if (StringUtils.hasText(bodyre))
                {
                    errorCode = getsetResponse.jsonPath().getString("code") == null ? "" : getsetResponse.jsonPath().getString(
                            "code");                   
                }
                else
                {
                    errorCode = "";
                }

                break; 
                
            case "GETDATATABLE":
                final Response gettableResponse = submitGETTableRequest(resources);
                String bodytable = gettableResponse.body().asString();
                statusCode = Integer.toString(gettableResponse.getStatusCode());
                		
                if (StringUtils.hasText(bodytable))
                {
                    //errorCode = gettableResponse.jsonPath().getString("code") == null ? "" : gettableResponse.jsonPath().getString(
                      //      "code");
                	errorCode = gettableResponse.body().path("quandl_error.code").toString() == null ? "" : gettableResponse.body().path("quandl_error.code").toString();
                	
                    System.out.println(errorCode);
                }
                else
                {
                    errorCode = "";
                }

                break;   

        }

        return response;

    }
	
	  private Response submitGETTableRequest(final Object... resources) 
		   throws Exception
			  {
				  final Constants constant = (Constants) resources[0];
			       
			        final String getDataTableURL = constant.getUrl()+constant.getVersion()+constant.getDatatableAPI()+constant.getDatatable()+"."+constant.getReturnFormat()+"?api_key="+constant.getApiKey();
			        System.out.println("get datatable url is "+ getDataTableURL); 
		         
			        try
			        {
			            
			            response =  given().when().get(getDataTableURL);

			            System.out.println("Response code is "+ response.getStatusCode());
			            
			            if (response != null)
			            {
			               
			                    if (response.jsonPath().getString("code") == null)
			                    {
			                        //result = "Successful";
			                        if (response.jsonPath().getString("column_names") != null)
			                        {
			                            // Do nothing return the response
			                        }
			                    }
			            }
			            return response;

			        }
			        catch (final Exception e)
			        {
			        	 System.err.println("there's an exception thrown in setting proxy!:" + e.getLocalizedMessage());
			            return null;
			        }
}

	private Response submitGETSETRequest(final Object... resources)
	           throws Exception
	  {
		  final Constants constant = (Constants) resources[0];
	       
	        final String getDataSetURL = constant.getUrl()+constant.getVersion()+constant.getDataset()+constant.getDatasetFB()+"."+constant.getReturnFormat();
	        System.out.println("get dataset url is "+ getDataSetURL); 
         
	        try
	        {
	            
	            response =  given().when().get(getDataSetURL);

	            System.out.println("Response code is "+ response.getStatusCode());
	            
	            if (response != null)
	            {
	               
	                    if (response.jsonPath().getString("code") == null)
	                    {
	                        //result = "Successful";
	                        if (response.jsonPath().getString("column_names") != null)
	                        {
	                            // Do nothing return the response
	                        }
	                    }
	            }
	            return response;

	        }
	        catch (final Exception e)
	        {
	        	 System.err.println("there's an exception thrown in setting proxy!:" + e.getLocalizedMessage());
	            return null;
	        }
	    }


	public Response submitGETRequest(final Object... resources)
	            throws Exception
	    {
		    final Constants constant = (Constants) resources[0];
	       
	        final String getDataBaseURL = constant.getUrl()+constant.getVersion()+constant.getDatabase()+"."+constant.getReturnFormat();
	        System.out.println("get database url is "+ getDataBaseURL); 
           
	        try
	        {
	            
	            response =  given().when().get(getDataBaseURL);

	            System.out.println("Response code is "+ response.getStatusCode());
	            
	            if (response != null)
	            {
	               
	                    if (response.jsonPath().getString("code") == null)
	                    {
	                        //result = "Successful";
	                        if (response.jsonPath().getString("url_name") != null)
	                        {
	                            // Do nothing return the response
	                        }
	                    }
	            }
	            return response;

	        }
	        catch (final Exception e)
	        {
	        	 System.err.println("there's an exception thrown in setting proxy!:" + e.getLocalizedMessage());
	            return null;
	        }
	    }


   
    public void validate(final String expectedErrorCode, final String expectedStatusCode, String submitType)
    {
       
    	assertTrue(
    			"Got unexpected status code: " + statusCode + " Expected:" + expectedStatusCode,
    			expectedStatusCode.equals(statusCode));

    	assertTrue("Got unexpected error code: " + errorCode + ", Expected:" + expectedErrorCode,
    			expectedErrorCode.equals(errorCode));

    	switch (submitType)
    	{
    	case "GETDATABASE":
    		if (statusCode.equals(200)) {
    			int sizeOfDatabases = response.body().path("databases.size()");

    			assertTrue(sizeOfDatabases > 0); }

    		break;

    	case "GETDATASET":

    		if (statusCode.equals(200)) {
    			int sizeOfDatasets = response.body().path("dataset_data.size()");

    			assertTrue(sizeOfDatasets > 0);
    		}

    		break;  

    	case "GETDATATABLE":

    		if (statusCode.equals(200)) {

    			int sizeOfDatatable = response.body().path("datatable.size()");

    			assertTrue(sizeOfDatatable > 0);
    		}

    		break;   


        }

    	
    	
        
       

    }



}
