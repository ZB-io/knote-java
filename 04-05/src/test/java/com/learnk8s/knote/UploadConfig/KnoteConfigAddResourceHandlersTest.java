
// ********RoostGPT********
/*
Test generated by RoostGPT for test aman15thMaygitlab using AI Type  and AI Model

ROOST_METHOD_HASH=addResourceHandlers_48286cc0db
ROOST_METHOD_SIG_HASH=addResourceHandlers_289a4a6d21

"""
Scenario 1: Test if the resource handler is correctly added with the "/uploads/**" path pattern

Details:
  TestName: testResourceHandlerAddition.
  Description: This test is meant to check if the "addResourceHandler" method is correctly adding the resource handler with the "/uploads/**" path pattern.
Execution:
  Arrange: Mock the ResourceHandlerRegistry and the Properties class to return a specific upload directory path.
  Act: Invoke the addResourceHandlers method with the mocked registry.
  Assert: Use JUnit assertions to check if the "/uploads/**" path pattern has been added to the registry.
Validation:
  The assertion verifies that the resource handler has been added correctly with the "/uploads/**" path pattern. This is important as it ensures that the application will be able to correctly handle requests to the "/uploads/**" path.

Scenario 2: Test if the resource location is correctly set

Details:
  TestName: testResourceLocationSetting.
  Description: This test is meant to check if the "addResourceLocations" method is correctly setting the resource location to the upload directory path.
Execution:
  Arrange: Mock the ResourceHandlerRegistry and the Properties class to return a specific upload directory path.
  Act: Invoke the addResourceHandlers method with the mocked registry.
  Assert: Use JUnit assertions to check if the resource location has been set to the correct upload directory path.
Validation:
  The assertion verifies that the resource location is correctly set. This is important as it ensures that the application will be able to find the resources in the correct directory.

Scenario 3: Test if the cache period is correctly set

Details:
  TestName: testCachePeriodSetting.
  Description: This test is meant to check if the "setCachePeriod" method is correctly setting the cache period to 3600 seconds.
Execution:
  Arrange: Mock the ResourceHandlerRegistry and the Properties class to return a specific upload directory path.
  Act: Invoke the addResourceHandlers method with the mocked registry.
  Assert: Use JUnit assertions to check if the cache period has been set to 3600 seconds.
Validation:
  The assertion verifies that the cache period is correctly set. This is important as it ensures that the caching mechanism of the application works as expected.

Scenario 4: Test if the resource chain is correctly set

Details:
  TestName: testResourceChainSetting.
  Description: This test is meant to check if the "resourceChain" method is correctly setting the resource chain to true.
Execution:
  Arrange: Mock the ResourceHandlerRegistry and the Properties class to return a specific upload directory path.
  Act: Invoke the addResourceHandlers method with the mocked registry.
  Assert: Use JUnit assertions to check if the resource chain has been set to true.
Validation:
  The assertion verifies that the resource chain is correctly set. This is important as it ensures that the resource chain is enabled, allowing the application to correctly handle static resources.

Scenario 5: Test if the PathResourceResolver is correctly added

Details:
  TestName: testPathResourceResolverAddition.
  Description: This test is meant to check if the "addResolver" method is correctly adding the PathResourceResolver.
Execution:
  Arrange: Mock the ResourceHandlerRegistry and the Properties class to return a specific upload directory path.
  Act: Invoke the addResourceHandlers method with the mocked registry.
  Assert: Use JUnit assertions to check if the PathResourceResolver has been added.
Validation:
  The assertion verifies that the PathResourceResolver has been added correctly. This is important as it ensures that the application is able to correctly resolve the paths of the resources.
"""
*/

// ********RoostGPT********

package com.learnk8s.knote.UploadConfig;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.PathResourceResolver;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class KnoteConfigAddResourceHandlersTest {

	@Autowired
	private KnoteProperties properties;

	@Test
	@Tag("valid")
	public void testResourceHandlerAddition() {
		ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
		when(properties.getUploadDir()).thenReturn("uploads");
		KnoteConfig knoteConfig = new KnoteConfig(properties);
		knoteConfig.addResourceHandlers(registry);
		verify(registry, times(1)).addResourceHandler("/uploads/**");
	}

	@Test
	@Tag("valid")
	public void testResourceLocationSetting() {
		ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
		String uploadDir = "uploads";
		when(properties.getUploadDir()).thenReturn(uploadDir);
		KnoteConfig knoteConfig = new KnoteConfig(properties);
		knoteConfig.addResourceHandlers(registry);
		verify(registry, times(1)).addResourceLocations("file:" + uploadDir);
	}

	@Test
	@Tag("valid")
	public void testCachePeriodSetting() {
		ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
		when(properties.getUploadDir()).thenReturn("uploads");
		KnoteConfig knoteConfig = new KnoteConfig(properties);
		knoteConfig.addResourceHandlers(registry);
		verify(registry, times(1)).setCachePeriod(3600);
	}

	@Test
	@Tag("valid")
	public void testResourceChainSetting() {
		ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
		when(properties.getUploadDir()).thenReturn("uploads");
		KnoteConfig knoteConfig = new KnoteConfig(properties);
		knoteConfig.addResourceHandlers(registry);
		verify(registry, times(1)).resourceChain(true);
	}

	@Test
	@Tag("valid")
	public void testPathResourceResolverAddition() {
		ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
		when(properties.getUploadDir()).thenReturn("uploads");
		KnoteConfig knoteConfig = new KnoteConfig(properties);
		knoteConfig.addResourceHandlers(registry);
		verify(registry, times(1)).addResolver(any(PathResourceResolver.class));
	}

}