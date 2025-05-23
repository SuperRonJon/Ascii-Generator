import com.superronjon.ascii.AsciiGenerator;
import com.superronjon.inputparse.GenericInputParser;
import com.superronjon.inputparse.UnrecognizedOptionException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args){
		final String CURRENT_VERSION = "v2.8";

		GenericInputParser parser = createInputParser();

		try {
			parser.parseInput(args);
		}
		catch (UnrecognizedOptionException e) {
			System.out.println(e.getMessage());
			return;
		}

		if(parser.getOptionValue("version").equals("True")) {
			System.out.println("ascii-generator " + CURRENT_VERSION);
			return;
		}

		if(parser.getOptionValue("help").equals("True")) {
			parser.printHelp();
			return;
		}

		boolean invert = Boolean.parseBoolean(parser.getOptionValue('i'));
		boolean removeBorder = Boolean.parseBoolean(parser.getOptionValue('b'));
		double scalingFactor = Double.parseDouble(parser.getOptionValue('s'));
		double heightScaling = Double.parseDouble(parser.getOptionValue('h'));
		double widthScaling = Double.parseDouble(parser.getOptionValue('w'));

		String outputFileName = parser.getOptionValue("to-file");
		boolean toFile = false;
		if(!outputFileName.trim().isEmpty()) {
			toFile = true;
		}

		boolean widthGiven = heightScaling > 0;
		boolean heightGiven = widthScaling > 0;

		String inputFileName = parser.getUnflaggedArgument(0);
		if(inputFileName == null) {
			System.out.println("No input file given");
			return;
		}
		File inputFile = new File(parser.getUnflaggedArgument(0));

        BufferedImage image;
        try {
            image = ImageIO.read(inputFile);
        } catch (IOException e) {
            System.out.println("Unable to read input file");
            return;
        }

		AsciiGenerator generator = null;
		if(!widthGiven && !heightGiven) {
			generator = new AsciiGenerator(image, scalingFactor);
		}
		else if(widthScaling > 0 && heightScaling > 0) {
			generator = new AsciiGenerator(image, widthScaling, heightScaling);
		}
		else {
			System.out.println("Invalid scaling parameters. \nIf not using equivalent scaling for the height and width (s)," +
				"\nboth height (h) and width (w) parameters must be supplied and be greater than 0.");
			return;
		}

        if(toFile) {
			File outputFile = new File(outputFileName);
			System.out.println("Writing to " + outputFile.getAbsolutePath());
            generator.writeToFile(outputFileName, invert, removeBorder);
        }
        else {
            System.out.print(generator.toString(invert, removeBorder));
        }
    }

	private static GenericInputParser createInputParser()
	{
		GenericInputParser parser = new GenericInputParser("ascii-generator", "ascii-generator [OPTIONS...] image/file/path.jpg");
		parser.addOption('i', "invert", "Invert color so that the brightest pixels use the denses characters");
		parser.addOption('b', "remove-border", "Removes border that sometimes appears on non-inverted images");
		parser.addOption('f', "to-file", true, "", "Output to file, takes output filepath as VAL");
		parser.addOption('s', "scaling", true, "1.0", "Used to scale the images height and width evenly by VAL");
		parser.addOption('w', "width", true, "-1.0", "Used to scale the height separately from width. Both must be given");
		parser.addOption('h', "height", true, "-1.0", "Used to scale the height separately from width. Both must be given");
		parser.addOption('v', "version", "Print version number");
		parser.addOption('H', "help", "Print help menu");
		return parser;
	}


}
