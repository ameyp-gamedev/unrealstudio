package com.wirywolf.validation;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.NamesAreUniqueValidator;

import com.google.inject.Inject;

public class UnrealscriptNamesAreUniqueValidator extends NamesAreUniqueValidator
{
	@Inject
	private IResourceServiceProvider.Registry resourceServiceProviderRegistry = IResourceServiceProvider.Registry.INSTANCE;
	
	@Inject
	private UnrealscriptNamesAreUniqueValidationHelper helper;

	@Override
	public void doCheckUniqueNames(Resource resource, CancelIndicator cancelIndicator) {
		final IResourceServiceProvider resourceServiceProvider = resourceServiceProviderRegistry.getResourceServiceProvider(resource.getURI());
		if (resourceServiceProvider==null)
			return;
		IResourceDescription.Manager manager = resourceServiceProvider.getResourceDescriptionManager();
		if (manager != null) {
			IResourceDescription description = manager.getResourceDescription(resource);
			if (description != null) {
				Iterable<IEObjectDescription> descriptions = description.getExportedObjects();
				helper.checkUniqueNames(descriptions, cancelIndicator, this);
			}
		}
	}

}
