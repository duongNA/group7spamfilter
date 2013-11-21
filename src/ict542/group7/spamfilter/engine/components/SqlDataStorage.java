package ict542.group7.spamfilter.engine.components;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ict542.group7.spamfilter.engine.common.Constants;
import ict542.group7.spamfilter.engine.common.Feature;
import ict542.group7.spamfilter.engine.utils.SqlUtils;

public class SqlDataStorage extends AbstractDataStorage {
	
	private static final Logger logger = Logger.getLogger(SqlDataStorage.class);

	@Override
	public void addFeature(String tokenString, int emailType) {
		logger.debug("add feature: " + tokenString);
		boolean isNewFeature = false;
		Feature feature = SqlUtils.searchFeatureByTokenString(tokenString);
		if (feature == null) {
			feature = new Feature(tokenString);
			isNewFeature = true;
		}
	
		if (emailType == Constants.SPAM_EMAIL) {
			feature.increaseOccurInSpamByOne();
		}
		if (emailType == Constants.HAM_EMAIL) {
			feature.increaseOccurInHamByOne();
		}
		
		if (isNewFeature) {
			SqlUtils.insertFeature(feature);
		} else {
			SqlUtils.updateFeature(feature);
		}
	}

	@Override
	public Feature findFeature(String tokenString) {
		return SqlUtils.searchFeatureByTokenString(tokenString);
	}

	@Override
	public void computeFeatureProbabilities(int totalOfSpam, int totalOfHam) {
		logger.debug("start computing probabilities");
		Connection c = SqlUtils.getConnection();
		Statement statement = null;
		String sql = "SELECT * FROM Feature";
		try {
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Feature f = new Feature(rs.getString("token_string"));
				f.setOccurInSpam(rs.getInt("occur_in_spam"));
				f.setOccurInHam(rs.getInt("occur_in_ham"));
				f.computeProbability(totalOfSpam, totalOfHam);
				
				rs.updateDouble("probability", f.getPropability());
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		
		logger.debug("computing probabilities is completed!!!");
	}

	@Override
	public Map<String, Feature> getFeatureMap() {
		return new HashMap<String, Feature>();
	}

	@Override
	public void clearData() {
		SqlUtils.clearTableFeature();
	}
	
}
