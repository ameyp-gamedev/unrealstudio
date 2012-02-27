package com.wirywolf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.naming.SimpleNameProvider;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.inject.Inject;
import com.wirywolf.unrealscript.ClassDecl;
import com.wirywolf.unrealscript.EnumDeclaration;
import com.wirywolf.unrealscript.FunctionBody;
import com.wirywolf.unrealscript.Model;
import com.wirywolf.unrealscript.StructDeclaration;
import com.wirywolf.unrealscript.VarName;

public class UnrealscriptQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider 
{
	QualifiedName qualifiedName(VarName v)
	{
		EObject parent = v.eContainer();
		QualifiedName qn = QualifiedName.create(v.getName());

		if (parent instanceof EnumDeclaration)
		{
			qn = QualifiedName.create(v.getName());
		}
		else
		{
			qn = getFullyQualifiedName(parent).append(v.getName());
		}

//		if (found == false)
//		{
//			qn = super.getFullyQualifiedName(v);
//		}
		return qn;
	}
}
