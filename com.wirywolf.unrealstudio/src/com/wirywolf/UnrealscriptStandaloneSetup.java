
package com.wirywolf;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class UnrealscriptStandaloneSetup extends UnrealscriptStandaloneSetupGenerated{

	public static void doSetup() {
		new UnrealscriptStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

