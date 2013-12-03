package ict542.group7.spamfilter.engine.components;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * This class handles extracting features from an email 
 */
public class FeatureExtractor {
	private static final Logger logger = Logger.getLogger(FeatureExtractor.class);
	
	public static final int FOR_TRAINING = 0;
	public static final int FOR_ANALYSIS = 1;
	
	private AbstractDataStorage dataStorage;
	private AnalysisEngine analysisEngine;
	
	public FeatureExtractor(AbstractDataStorage dataStorage, AnalysisEngine analysisEngine) {
		this.dataStorage = dataStorage;
		this.analysisEngine = analysisEngine;
	}
	
	public String readFileContent(String filePath) throws IOException {
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = null;
			StringBuilder builder = new StringBuilder();
		
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			return builder.toString();
		} catch (IOException ioE) {
			throw ioE;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {}
			}
		}
	}
	
	public void extractFeatures(String filePath, Integer emailType, int isFor) {

		try {
			FileReader reader = new FileReader(filePath);
			Scanner scanner = new Scanner(reader);
			scanner.useDelimiter(Constants.EMAIL_DELIMITERS_FOR_SCANNER);

			while (scanner.hasNext()) {
				String token = scanner.next();
				
				// skip the token if it's too short or contain only digits
//				if (token.length() < Constants.MIN_TOKEN_LENG || StringUtils.containsOnlyDigits(token)) {
//					continue;
//				}
				
				if (isFor == FOR_TRAINING) {
					// add feature to storage
					dataStorage.addFeature(token, emailType);
				} else if (isFor == FOR_ANALYSIS) {
					// add email feature to analysis engine
					analysisEngine.addEmailFeature(token);
				}
			}
			
			scanner.close();

		} catch (IOException ioException) {
			logger.debug("Error occur when read file: " + filePath, ioException);
			return;
		}
	}
}
