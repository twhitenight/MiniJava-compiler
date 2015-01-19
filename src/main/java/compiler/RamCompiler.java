package compiler;import symboltable.Table;import syntaxtree.Program;import frontend.generated.*;import java.io.File;import java.io.FileInputStream;import java.io.FileNotFoundException;import java.io.InputStream;import java.io.PrintStream;import java.io.PrintWriter;import org.apache.commons.cli.BasicParser;import org.apache.commons.cli.CommandLine;import org.apache.commons.cli.Option;import org.apache.commons.cli.Options;import org.apache.commons.cli.ParseException;import visitor.*;public class RamCompiler{	static PrintWriter debug = new PrintWriter(System.out);	public static Options getCliOptions()	{		Options options = new Options();		options.addOption(new Option("o", "output", true, "output file."));		options.addOption(new Option("i", "input", true, "input file."));		options.addOption(new Option("f", "format", true,				"Output format. Valid options: x86, python, mips"));		return options;	}	public static Visitor getVisitorForFormatString(String format, PrintStream ps,			Table symTable)	{		format = format.toUpperCase();		if (format.equals("X86"))			return new X86CodeGenerator(ps, symTable);		else if (format.equals("PYTHON"))			return new PythonVisitor(ps, symTable);		else if (format.equals("MIPS"))			return new MipsCodeGenerator(ps, symTable);		else			throw new IllegalArgumentException(					"Invalid output format specified. Valid options are: x86, python, mips");	}	public static void main(String[] args) throws FileNotFoundException,			ParseException, frontend.generated.ParseException	{		BasicParser parser = new BasicParser();		CommandLine options = parser.parse(getCliOptions(), args);		Program root = null;		try		{			InputStream in = System.in;			if (options.hasOption('i'))				in = new FileInputStream(new File(options.getOptionValue('i')));			root = new frontend.generated.RamParser(in).Goal();		}		catch (frontend.generated.ParseException e)		{			System.err.println(e.toString());			return;		}		catch (java.io.FileNotFoundException e)		{			System.err.println("File Not Found: " + e);			return;		}		BuildSymbolTableVisitor v = new BuildSymbolTableVisitor(); // note that																	// this is																	// necessary																	// so we																	// remember																	// our table		root.accept(v); // build symbol table		// perform type checking		TypeCheckVisitor typeChecker = new TypeCheckVisitor(v.getSymTab());		root.accept(typeChecker);		if (typeChecker.getErrorMsg().getHasErrors())		{			System.out.println("Error in front-end. Exiting.");			System.exit(1);		}		java.io.PrintStream ps = System.out;		if (options.hasOption('o'))			ps = new java.io.PrintStream(new java.io.FileOutputStream(					options.getOptionValue('o')));		Visitor codeGenerator;		if (options.hasOption('f'))			codeGenerator = getVisitorForFormatString(options.getOptionValue('f'),					ps, v.getSymTab());		else			codeGenerator = new X86CodeGenerator(ps, v.getSymTab());		root.accept(codeGenerator);	}}