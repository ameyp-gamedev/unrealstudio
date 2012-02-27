package com.wirywolf.util;

import com.wirywolf.unrealscript.FunctionArg;
import com.wirywolf.unrealscript.UnrealscriptFactory;
import com.wirywolf.unrealscript.VarName;

public class TypeUtils {
	public VarName createVarName(String varName)
	{
		VarName vn = UnrealscriptFactory.eINSTANCE.createVarName();
		vn.setName(varName);
		return vn;
	}
	
	public FunctionArg createFunctionArg(String varName)
	{
		FunctionArg fa = UnrealscriptFactory.eINSTANCE.createFunctionArg();
		fa.setName(varName);
		return fa;
	}
}
