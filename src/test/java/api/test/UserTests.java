package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payloads.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userpayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userpayload = new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setPassword(faker.internet().password());
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		userpayload.setUsername(faker.name().username());
		
		//logs
		logger= LogManager.getLogger(this.getClass());
		
		
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("*************** creating user *******************");
		Response response = UserEndpoints.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*************** user creatd *******************");
		
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("*************** getting user *******************");
		Response response = UserEndpoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************** retrieved user *******************");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		//update data with payload
		logger.info("*************** updating user *******************");
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndpoints.updateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking after update
		Response responseAfterUpdate = UserEndpoints.readUser(this.userpayload.getUsername());
		responseAfterUpdate.then().log().body();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		logger.info("*************** user updated *******************");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		logger.info("*************** deleting user *******************");
		Response response = UserEndpoints.deleteUser(this.userpayload.getUsername());
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************** user deleted *******************");
		
	}
	
	

}
