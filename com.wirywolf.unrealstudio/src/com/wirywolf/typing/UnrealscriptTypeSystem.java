package com.wirywolf.typing;

import org.eclipse.emf.ecore.EObject;

import com.wirywolf.unrealscript.DeclaredVariable;
import com.wirywolf.unrealscript.QualifiedIdentifier;
import com.wirywolf.unrealscript.VarName;
import com.wirywolf.unrealscript.util.UnrealscriptSwitch;

public class UnrealscriptTypeSystem extends UnrealscriptSwitch<TypeResult>
{
	public TypeResult getType(EObject eObject)
	{
		TypeResult result = doSwitch(eObject);
		if (result == null || result.getType() == null)
		{
			result = new TypeResult("cannot type");
		}
		
		return result;
	}
	
	/**
	 * Returns the result of interpreting the object as an instance of '<em>Qualified Identifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Qualified Identifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@Override
	public TypeResult caseQualifiedIdentifier(QualifiedIdentifier object)
	{
		EObject child = object.getChild();
		return getType(child);
	}
	
	@Override
	public TypeResult caseVarName(VarName object)
	{
		return getType(object);
	}
	
	public TypeResult caseDeclaredVariable(DeclaredVariable object)
	{
		return getType(object);
	}
}
