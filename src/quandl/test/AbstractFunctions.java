package quandl.test;

public interface AbstractFunctions {

	public void login(String baseUrl, String userName, String password,
			String browser) throws Exception;

	public void setEnvironment() throws Exception;

	abstract void setup(String browser) throws Exception;

}
