package com.task08;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.LambdaUrlConfig;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaLayer;
import org.open.meteo.api.OpenMeteoApiClient;

import static com.syndicate.deployment.model.ArtifactExtension.ZIP;
import static com.syndicate.deployment.model.DeploymentRuntime.JAVA8;

@LambdaHandler(lambdaName = "api_handler",
		roleName = "api_handler-role",
		layers = "sdk-layer"
)
@LambdaLayer(layerName = "sdk-layer",
		libraries = "lib/open-meteo-api-sdk-1.0-SNAPSHOT.jar",
		runtime = JAVA8,
		artifactExtension = ZIP
)
@LambdaUrlConfig
public class ApiHandler implements RequestHandler<Object, String> {

	public String handleRequest(Object request, Context context) {
		OpenMeteoApiClient openMeteoApiClient = new OpenMeteoApiClient();
		LambdaLogger logger = context.getLogger();

		String apiResult = openMeteoApiClient.getTokyoWeather();

		logger.log(String.format("Weather in Tokyo: %s", apiResult));
		return apiResult;
	}
}
