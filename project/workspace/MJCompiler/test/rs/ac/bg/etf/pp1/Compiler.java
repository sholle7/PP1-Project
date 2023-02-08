package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*; 
import rs.etf.pp1.symboltable.structure.*;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		
		if (args.length < 2) {
			log.error("Not enough arguments supplied! Usage: MJParser <source-file> <obj-file> ");
			return;
		}
		
		File sourceCode = new File(args[0]);
		if (!sourceCode.exists()) {
			log.error("Source file [" + sourceCode.getAbsolutePath() + "] not found!");
			return;
		}
		
		try {
			// opening .mj file
			//File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			MJParser p = new MJParser(lexer);
			
			// start parsing
	        Symbol s = p.parse(); 
	        
	        if(lexer.errorD == true) {
				log.info("Leksicka analiza neuspesno zavrsena! - postoji leksicka greska!");
				return;
			}
	        
	        if(p.errorDetected) {
	        	log.info("Parsiranje neuspesno zavrseno! - postoji sintaksna greska!");
	        }
	        else {
	        
		        Program prog = (Program)(s.value); 
		            
				// printing syntax tree
				log.info(prog.toString(""));
				log.info("======================================================================");
				
				// initializing symbols table
				Tab.init(); 
				
		        // adding boolean type to the symbols table
				Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", SemanticAnalyzer.boolType));
				
	
				// ispis prepoznatih programskih konstrukcija
				SemanticAnalyzer v = new SemanticAnalyzer();
				prog.traverseBottomUp(v); 
		      
				//log.info(" Print count calls = " + v.printCallCount);
	
				//log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
				
				//log.info("===================================");
				
				Tab.dump();
				
				if(!p.errorDetected && v.passed()){
					
					File objFile = new File(args[1]);
					log.info("Generating bytecode file: " + objFile.getAbsolutePath());
		        	if (objFile.exists())
		        		objFile.delete();
					
					/*File objFile = new File("test/program.obj");
					if(objFile.exists()) objFile.delete();
					*/
					
					CodeGenerator codeGenerator = new CodeGenerator();
					
					prog.traverseBottomUp(codeGenerator);
					Code.dataSize = v.globalVariablesCounter;
					Code.mainPc = codeGenerator.getMainPc();
					Code.write(new FileOutputStream(objFile));
					
					log.info("Parsiranje uspesno zavrseno!");
				}
				else{
					
					log.error("Parsiranje neuspesno zavrseno! - postoji semanticka greska");
				}
	        }
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
