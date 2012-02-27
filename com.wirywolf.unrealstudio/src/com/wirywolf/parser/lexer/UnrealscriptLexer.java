package com.wirywolf.parser.lexer;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

import com.wirywolf.parser.antlr.lexer.InternalUnrealscriptLexer;

public class UnrealscriptLexer extends InternalUnrealscriptLexer 
{
	protected DFA my_dfa3, my_dfa20;
	
    public UnrealscriptLexer() 
    {
    	super();
    	my_dfa3 = this.dfa3;
    	my_dfa20 = this.dfa20;
    }
    
    public UnrealscriptLexer(CharStream input) 
    {
        super(input);
        my_dfa3 = this.dfa3;
    	my_dfa20 = this.dfa20;
    }
    
    public UnrealscriptLexer(CharStream input, RecognizerSharedState state)
    {
        super(input,state);
        my_dfa3 = this.dfa3;
    	my_dfa20 = this.dfa20;
    }
    
	public void mTokens() throws RecognitionException 
	{
		if (my_dfa20.predict(input) == 189)
			Custom_mRULE_CPP_TEXT();
		else
			super.mTokens();
	}
        
	public final void Custom_mRULE_CPP_TEXT() throws RecognitionException 
	{
        try 
        {
            int _type = RULE_CPP_TEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            {
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='c') ) 
            {
                alt2=1;
            }
            else if ( (LA2_0=='s') ) 
            {
                alt2=2;
            }
            else 
            {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) 
            {
                case 1 :
                	match("cpptext");
                    break;
                case 2 :
	                match("structcpptext");
                    break;
            }

            loop3:
            do 
            {
                int alt3=2;
                alt3 = my_dfa3.predict(input);
                switch (alt3) 
                {
            	case 1 :
            	    // ../com.wirywolf.unrealstudio/src-gen/com/wirywolf/parser/antlr/lexer/InternalUnrealscriptLexer.g:335:73: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match('{'); 
            // ../com.wirywolf.unrealstudio/src-gen/com/wirywolf/parser/antlr/lexer/InternalUnrealscriptLexer.g:335:81: ( options {greedy=false; } : . )*
            int open_braces = 0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if (LA4_0=='}')
                {
                	if (open_braces > 0)
                	{
                		open_braces -= 1;
                		alt4 = 1;
                	}
                	else
                	{
                		alt4 = 2;
                	}
                }
                else if (LA4_0 == '{')
                {
                	open_braces += 1;
                	alt4 = 1;
                }
                else if ( ((LA4_0>='\u0000' && LA4_0<='|')||(LA4_0>='~' && LA4_0<='\uFFFF')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../com.wirywolf.unrealstudio/src-gen/com/wirywolf/parser/antlr/lexer/InternalUnrealscriptLexer.g:335:109: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            match('}');
            
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==';') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../com.wirywolf.unrealstudio/src-gen/com/wirywolf/parser/antlr/lexer/InternalUnrealscriptLexer.g:375:117: ';'
                    {
                    match(';'); 

                    }
                    break;

            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
}
