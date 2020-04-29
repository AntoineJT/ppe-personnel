package com.github.antoinejt.ppepersonnel.personnel;

/**
 * Exception levée lorsqu'il est impossible de sauvegarder le gestionnaire.
 */

public class SauvegardeImpossible extends Exception
{
	private final Exception exception;
	
	public SauvegardeImpossible(Exception exception)
	{
		this.exception = exception;
	}
	
	@Override
	public void printStackTrace() 
	{
			super.printStackTrace();
			System.err.println("Causé par : ");
			exception.printStackTrace();			
	}
}
