package org.leap;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.ws.ConnectionException;

public class LeapTask extends Task {
	
	String projectRootFolder = null;
	public void setProjectRoot(String root){
		this.projectRootFolder = root;
		if(!this.projectRootFolder.endsWith("/")){
			this.projectRootFolder += "/";
		}
	}
	
	public String getProjectRoot(){
		if(projectRootFolder == null){
			projectRootFolder = rootSourceFolder();
		}
		return this.projectRootFolder;
	}
	
	protected String rootSourceFolder(){
		String[] path = this.getLocation().getFileName().split("/");
		String result = "";
		for(String p : path){
			if(p.contains(".")){
				continue;
			}
			result += p + "/";
		}
		result += "src/";
		return result;
	}
	
	String username;
    public void setUsername(String uname) {
    	username = uname;
    }
    
    String password;
    public void setPassword(String pw) {
    	password = pw;
    }
    
    String token;
    public void setToken(String t) {
    	token = t;
    }
    
    String serverurl;
    public void setServerurl(String url) {
    	serverurl = url;
    }
    
    String api = "28.0";
    public void setApi(String apiVersion){
    	api = apiVersion;
    }
    
    String namespace = "";
    public void setNamespace(String ns){
    	namespace = ns;
    }
    
    // By default the generated files are deployed.
    // set it to false if don't want to deploy
    protected Boolean deployOption = true;
    public void setDeploy(Boolean deploy) {
    	deployOption = deploy;
    }
    
    private SalesforceConnection m_salesforceConnection = null;
    public SalesforceConnection salesforceConnection(){
    	if(m_salesforceConnection == null){
    		try {
				m_salesforceConnection = new SalesforceConnection(username, password, token, serverurl);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
    	}
    	return m_salesforceConnection;
    }
    
    public void validateConnectionParams(){
    	if(this.username == null){
    		throw new BuildException("Missing Salesforce username param");
    	}
    	if(this.password == null){
    		throw new BuildException("Missing Salesforce password param");
    	}
    	if(this.token == null){
    		throw new BuildException("Missing Salesforce token param");
    	}
    	if(this.serverurl == null){
    		throw new BuildException("Missing Salesforce serverurl param");
    	}
    }
    
    private DescribeGlobalSObjectResult[] m_sobjectResults;
	public DescribeGlobalSObjectResult[] sObjects(){
		if(m_sobjectResults == null){
			DescribeGlobalResult describeGlobalResult = null;
			try {
				describeGlobalResult = salesforceConnection().getPartnerConnection().describeGlobal();
			} catch (ConnectionException e) {
				e.printStackTrace();
				//errors.add(e.getMessage());
			}
			m_sobjectResults = describeGlobalResult.getSobjects();
		}
		return m_sobjectResults;
	}
	
	// deploy the generated files with Metadata connection
	protected void deployGeneratedFiles(List<String> metadataFiles) {
		
		MetadataConnection metadataConnection = salesforceConnection().getMetadataConnection();

		try {
			MetadataDeployer metadataDeployer = new MetadataDeployer(api, metadataConnection);
			metadataDeployer.deployMetadataFiles(metadataFiles);
		} catch(Exception e) {
			System.out.println("Deploying files failed with "+ e.getMessage() +", deploy is aborted");
//			e.printStackTrace();
		}
    	
	}
}
