package com.wirywolf.scoping;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.SimpleScope;

import com.wirywolf.unrealscript.ClassDecl;
import com.wirywolf.unrealscript.ClassParam;
import com.wirywolf.unrealscript.ConstDeclaration;
import com.wirywolf.unrealscript.Declaration;
import com.wirywolf.unrealscript.EnumDeclaration;
import com.wirywolf.unrealscript.FunctionCallType;
import com.wirywolf.unrealscript.FunctionDeclaration;
import com.wirywolf.unrealscript.InterfaceDecl;
import com.wirywolf.unrealscript.Model;
import com.wirywolf.unrealscript.StructDeclaration;
import com.wirywolf.unrealscript.StructVarDeclaration;
import com.wirywolf.unrealscript.VarDeclaration;
import com.wirywolf.unrealscript.VarName;
import com.wirywolf.unrealscript.VarType;

public class AuxiliaryFunctions 
{
	Model model;

	public IScope getStructDeclarationFieldScope(StructDeclaration structDeclaration)
	{
		List<VarName> fields = new LinkedList<VarName>();
		List<StructVarDeclaration> svDeclarations = structDeclaration.getStructVarDeclarations();
		for (StructVarDeclaration dv: svDeclarations)
		{
			fields.addAll(getFields(dv));
		}
		
		if (structDeclaration.getParentStruct() != null)
		{
			return new SimpleScope(getStructDeclarationFieldScope(structDeclaration.getParentStruct()),
					Scopes.scopedElementsFor(fields), true);
		}
		return new SimpleScope(Scopes.scopedElementsFor(fields), true);
	}
	
	public List<VarName> getFields(StructVarDeclaration structVarDeclaration)
	{
		List<VarName> fields = new LinkedList<VarName>();
		
		if (structVarDeclaration instanceof VarDeclaration)
		{
			VarDeclaration vd = (VarDeclaration)structVarDeclaration;
			fields.addAll(vd.getVarNames());
		}
		else if (structVarDeclaration instanceof ConstDeclaration)
		{			
			ConstDeclaration cd = (ConstDeclaration)structVarDeclaration;
			List<VarName> ll = new LinkedList<VarName>();
			ll.add(cd.getVarName());
			fields.addAll(ll);
		}
		
//		System.out.println("Resolved scope for " + structVarDeclaration + " to " + fields);
		return fields;
	}
	
	public List<VarName> getFields(Declaration declaration)
	{
		List<VarName> fields = new LinkedList<VarName>();
		
		if (declaration instanceof VarDeclaration)
		{
			VarDeclaration vd = (VarDeclaration)declaration;
			fields.addAll(vd.getVarNames());
		}
		else if (declaration instanceof ConstDeclaration)
		{			
			ConstDeclaration cd = (ConstDeclaration)declaration;
			List<VarName> ll = new LinkedList<VarName>();
			ll.add(cd.getVarName());
			fields.addAll(ll);
		}
		
//		System.out.println("Resolved scope for " + declaration + " to " + fields);
		return fields;
	}
	
	public List<VarName> getAllDeclarations(InterfaceDecl id)
	{
		List<Declaration> declarations = ((Model)(id.eContainer())).getDeclarations();
		List<VarName> fields = new LinkedList<VarName>();
		
		for (Declaration d: declarations)
		{
			fields.addAll(getFields(d));
		}
		
		List<ClassParam> classParams = id.getClassParams();
		for (ClassParam p: classParams)
		{
			if (p.getExtendedInterface() != null)
			{
				fields.addAll(getAllDeclarations(p.getImplementedInterface()));
			}
		}
		
		return fields;
	}
	
	public List<VarName> getAllDeclarations(ClassDecl cd)
	{
		List<Declaration> declarations = ((Model)(cd.eContainer())).getDeclarations();
		List<VarName> fields = new LinkedList<VarName>();
		
		for (Declaration d: declarations)
		{
			fields.addAll(getFields(d));
		}
		
		List<ClassParam> classParams = cd.getClassParams();
		for (ClassParam p: classParams)
		{
			if (p.getExtendedClass() != null)
			{
				fields.addAll(getAllDeclarations(p.getExtendedClass()));
			}
			else if (p.getWithinClass() != null)
			{
				fields.addAll(getAllDeclarations(p.getWithinClass()));
			}
			else if (p.getImplementedInterface() != null)
			{
				fields.addAll(getAllDeclarations(p.getImplementedInterface()));
			}
		}
		
		return fields;
	}
	
	public List<FunctionCallType> getAllMethods(ClassDecl cd)
	{
		Model m = (Model)(cd.eContainer());
		List<FunctionCallType> methods = new LinkedList<FunctionCallType>();
		
		List<EObject> declarations = m.getBody().getDeclarations();
		for (EObject e: declarations)
		{
			if (e instanceof FunctionDeclaration)
				methods.add((FunctionDeclaration)e);
		}
		
		List<ClassParam> classParams = cd.getClassParams();
		for (ClassParam p: classParams)
		{
			if (p.getExtendedClass() != null)
			{
				methods.addAll(getAllMethods(p.getExtendedClass()));
			}
			else if (p.getWithinClass() != null)
			{
				methods.addAll(getAllMethods(p.getWithinClass()));
			}
			else if (p.getImplementedInterface() != null)
			{
				methods.addAll(getAllMethods(p.getImplementedInterface()));
			}
		}
		
		return methods;
	}
	
	public List<FunctionCallType> getAllMethods(InterfaceDecl id)
	{
		Model m = (Model)(id.eContainer());
		List<FunctionCallType> methods = new LinkedList<FunctionCallType>();
		
		List<EObject> declarations = m.getBody().getDeclarations();
		for (EObject e: declarations)
		{
			if (e instanceof FunctionDeclaration)
				methods.add((FunctionDeclaration)e);
		}
		
		List<ClassParam> classParams = id.getClassParams();
		for (ClassParam p: classParams)
		{
			if (p.getImplementedInterface() != null)
			{
				methods.addAll(getAllMethods(p.getImplementedInterface()));
			}
		}
		
		return methods;
	}
	
	public IScope getVarTypeFieldScope(VarType varType)
	{
		IScope finalScope = IScope.NULLSCOPE;
		List<VarName> fields = new LinkedList<VarName>();
		
		if (varType instanceof StructDeclaration)
		{
			finalScope = getStructDeclarationFieldScope((StructDeclaration)varType);
		}
		else if (varType instanceof ClassDecl)
		{
			ClassDecl c = (ClassDecl)varType;
			finalScope = new SimpleScope(Scopes.scopedElementsFor(getAllDeclarations(c)), true);
		}
		else if (varType instanceof EnumDeclaration)
		{
			fields.addAll(((EnumDeclaration)varType).getEnumOptions());
			finalScope = new SimpleScope(Scopes.scopedElementsFor(fields), true);
		}
//		System.out.println("Resolved scope for " + varType + " to " + fields);
		return finalScope;
	}
	
	public IScope getMethodScope(VarType varType)
	{
		IScope finalScope = IScope.NULLSCOPE;
		
		if (varType instanceof ClassDecl)
		{
			ClassDecl cd = (ClassDecl)varType;
			finalScope = new SimpleScope(Scopes.scopedElementsFor(getAllMethods(cd)), true);
		}
//		System.out.println("Resolved scope for " + varType + " to " + fields);
		return finalScope;
	}
}
