package com.wirywolf.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.NamesAreUniqueValidationHelper;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.common.collect.Maps;
import com.wirywolf.unrealscript.FunctionArg;
import com.wirywolf.unrealscript.FunctionDeclaration;

public class UnrealscriptNamesAreUniqueValidationHelper extends
		NamesAreUniqueValidationHelper 
{
	@Override
	public void checkUniqueNames(Iterable<IEObjectDescription> descriptions, 
			CancelIndicator cancelIndicator, 
			ValidationMessageAcceptor acceptor) 
	{
		Iterator<IEObjectDescription> iter = descriptions.iterator();
		if (!iter.hasNext())
			return;
		Map<EClass, Map<QualifiedName, IEObjectDescription>> clusterToNames = Maps.newHashMap();
		
		while(iter.hasNext()) 
		{
			IEObjectDescription description = iter.next();
			
			checkDescriptionForDuplicatedName(description, clusterToNames, acceptor);
			if (cancelIndicator != null && cancelIndicator.isCanceled())
				return;
		}
	}
	
	@Override
	protected void checkDescriptionForDuplicatedName(
			IEObjectDescription description,
			Map<EClass, Map<QualifiedName, IEObjectDescription>> clusterTypeToName,
			ValidationMessageAcceptor acceptor) 
	{
		EObject object = description.getEObjectOrProxy();
		EClass eClass = object.eClass();
		QualifiedName qualifiedName = description.getName();
		
		if (object instanceof FunctionDeclaration)
		{
			FunctionDeclaration fd = (FunctionDeclaration)object;
			
			if (fd.getFunctionType().equals("operator") ||
				fd.getFunctionType().equals("preoperator") ||
				fd.getFunctionType().equals("postoperator"))
			{
				return;
			}
			List<String> argList = new ArrayList<String>();
			
			if (fd.getBasicType() != null)
				argList.add(fd.getBasicType());
			else if (fd.getType() != null)
				argList.add(fd.getType().getName());
			else
				argList.add("void");
			
			argList.add(fd.getName());
			
			List<FunctionArg> functionArgList = fd.getFunctionArgList();
			
			for (FunctionArg arg: functionArgList)
			{
				if (arg.getBasicType() != null)
					argList.add(arg.getBasicType());
				else if (arg.getType() != null)
					argList.add(arg.getType().getName());
				else
					argList.add("void");
				
				if (arg.getBasicTemplateType() != null)
					argList.add(arg.getBasicTemplateType());
				else if (arg.getTemplateType() != null)
					argList.add(arg.getTemplateType().getName());
			}
			
			Object[] objectArray = argList.toArray();
			String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
			qualifiedName = QualifiedName.create(stringArray);
		}
		else if (object instanceof FunctionArg)
		{
			return;
			/*
			FunctionArg fa = (FunctionArg)object;
			FunctionDeclaration fd = (FunctionDeclaration)(object.eContainer());
			
			List<String> argList = new ArrayList<String>();
			
			if (fd.getBasicType() != null)
				argList.add(fd.getBasicType());
			else if (fd.getType() != null)
				argList.add(fd.getType().getName());
			else
				argList.add("void");
			argList.add(fd.getName());

			if (fa.getBasicType() != null)
				argList.add(fa.getBasicType());
			else if (fa.getType() != null)
				argList.add(fa.getType().getName());
			else
				argList.add("void");
			
			if (fa.getBasicTemplateType() != null)
				argList.add(fa.getBasicTemplateType());
			else if (fa.getTemplateType() != null)
				argList.add(fa.getTemplateType().getName());
			
			argList.add(fa.getName());
			
			Object[] objectArray = argList.toArray();
			String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
			qualifiedName = QualifiedName.create(stringArray);
			*/
		}
		
		EClass clusterType = getAssociatedClusterType(eClass);
		
		Map<QualifiedName, IEObjectDescription> nameToDescription = clusterTypeToName.get(clusterType);
		if (nameToDescription == null) 
		{
			nameToDescription = Maps.newHashMap();
			nameToDescription.put(qualifiedName, description);
			clusterTypeToName.put(clusterType, nameToDescription);
		}
		else 
		{
			if (nameToDescription.containsKey(qualifiedName)) 
			{
				IEObjectDescription prevDescription = nameToDescription.get(qualifiedName);
				
				if (prevDescription != null) 
				{
					createDuplicateNameError(prevDescription, clusterType, acceptor);
					nameToDescription.put(qualifiedName, null);
				}
				createDuplicateNameError(description, clusterType, acceptor);
			} 
			else 
			{
				nameToDescription.put(qualifiedName, description);
			}
		}
	}
}
