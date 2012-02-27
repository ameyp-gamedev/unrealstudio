/**
 * 
 */
package com.wirywolf.typing;

import com.wirywolf.unrealscript.VarType;
import com.wirywolf.util.TypeUtils;

/**
 * Represents the result of the typing.
 * 
 * @author bettini
 * 
 */
public class TypeResult {
	VarType type;

	String diagnostic;

	public TypeResult(VarType type) {
		super();
		this.type = type;
	}

	public TypeResult(String diagnostic) {
		super();
		this.diagnostic = diagnostic;
	}

	public TypeResult() {
	}

	public VarType getType() {
		return type;
	}

	public void setType(VarType type) {
		this.type = type;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public void addDiagnostic(String diagnostic) {
		if (this.diagnostic == null) {
			this.diagnostic = diagnostic;
		} else {
			this.diagnostic.concat("\n" + diagnostic);
		}
	}
}
