package com.dasinong.farmerClub.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class FullTextSearch {
	private final int NUM = 30; // max search result items
	private final int HIGHLIGHT_LEN = 20; // highlight content length
	private SimpleHTMLFormatter highlightFormatter;
	private String path;
	private String tableName;
	private Logger logger = LoggerFactory.getLogger(FullTextSearch.class);

	public FullTextSearch(String tableName, String path) {
		this.tableName = tableName;
		this.path = path;
		this.highlightFormatter = null;
	}

	private IndexWriter getIndexWriter(Directory dir, Analyzer analyzer) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46, analyzer);
		return new IndexWriter(dir, config);
	}

	public void createVarietyIndex() {
		Analyzer analyzer = new IKAnalyzer(true);
		Directory directory = null;
		IndexWriter iwriter = null;

		try {
			directory = FSDirectory.open(new File(this.path));
			iwriter = getIndexWriter(directory, analyzer);
		} catch (IOException e) {
			this.logger.error("open index file failed", e);
		}

		try {

			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = (Connection) DriverManager.getConnection(
					"jdbc:MySQL://localhost:3306/farmer_club?useUnicode=true&characterEncoding=UTF-8", "root",
					"weather123");
			Statement stmt;
			stmt = (Statement) con.createStatement();
			String selectSql = "SELECT * FROM variety";
			ResultSet res = stmt.executeQuery(selectSql);
			while (res.next()) {
				Document doc = new Document();

				String varietyId = res.getString("varietyId");
				FieldType ft = new FieldType();
				ft.setIndexed(false);
				ft.setStored(true);
				ft.setTokenized(false);
				doc.add(new Field("varietyId", varietyId, ft)); // id only need
																// to store, no
																// index, no 分词

				String varietyName = res.getString("varietyName") == null ? "" : res.getString("varietyName");
				doc.add(new TextField("varietyName", varietyName, Store.YES));
				String subId = res.getString("subId") == null ? "" : res.getString("subId");
				doc.add(new TextField("subId", subId, Store.YES));
				String registerationId = res.getString("registerationId") == null ? ""
						: res.getString("registerationId");
				doc.add(new TextField("registerationId", registerationId, Store.YES));
				String varietySource = res.getString("varietySource") == null ? "" : res.getString("varietySource");
				doc.add(new TextField("varietySource", varietySource, Store.YES));
				String characteristics = res.getString("characteristics") == null ? ""
						: res.getString("characteristics");
				doc.add(new TextField("characteristics", characteristics, Store.YES));
				String yieldPerformance = res.getString("yieldPerformance") == null ? ""
						: res.getString("yieldPerformance");
				doc.add(new TextField("yieldPerformance", yieldPerformance, Store.YES));
				String suitableArea = res.getString("suitableArea") == null ? "" : res.getString("suitableArea");
				doc.add(new TextField("suitableArea", suitableArea, Store.YES));

				iwriter.addDocument(doc);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (iwriter != null) {
			try {
				iwriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void createCPProductIndex() {
		Analyzer analyzer = new IKAnalyzer(true);
		Directory directory = null;
		IndexWriter iwriter = null;

		try {
			directory = FSDirectory.open(new File(this.path));
			iwriter = getIndexWriter(directory, analyzer);
		} catch (IOException e) {
			this.logger.error("open index file failed", e);
		}

		try {

			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = (Connection) DriverManager.getConnection(
					"jdbc:MySQL://localhost:3306/ploughHelper?useUnicode=true&characterEncoding=UTF-8", "root",
					"weather123");
			Statement stmt;
			stmt = (Statement) con.createStatement();
			String selectSql = "SELECT * FROM cpproduct";
			ResultSet res = stmt.executeQuery(selectSql);
			while (res.next()) {
				Document doc = new Document();

				String cPProductId = res.getString("cPProductId");
				FieldType ft = new FieldType();
				ft.setIndexed(false);
				ft.setStored(true);
				ft.setTokenized(false);
				doc.add(new Field("cPProductId", cPProductId, ft)); // id only
																	// need to
																	// store, no
																	// index, no
																	// 分词

				String cPProductName = res.getString("cPProductName") == null ? "" : res.getString("cPProductName");
				doc.add(new TextField("cPProductName", cPProductName, Store.YES));
				String registerationId = res.getString("registerationId") == null ? ""
						: res.getString("registerationId");
				doc.add(new TextField("registerationId", registerationId, Store.YES));
				String activeIngredient = res.getString("activeIngredient") == null ? ""
						: res.getString("activeIngredient");
				doc.add(new TextField("activeIngredient", activeIngredient, Store.YES));
				String type = res.getString("type") == null ? "" : res.getString("type");
				doc.add(new TextField("type", type, Store.YES));
				String manufacturer = res.getString("manufacturer") == null ? "" : res.getString("manufacturer");
				doc.add(new TextField("manufacturer", manufacturer, Store.YES));
				String tip = res.getString("tip") == null ? "" : res.getString("tip");
				doc.add(new TextField("tip", tip, Store.YES));
				String guideline = res.getString("guideline") == null ? "" : res.getString("guideline");
				doc.add(new TextField("guideline", guideline, Store.YES));
				String crop = res.getString("crop") == null ? "" : res.getString("crop");
				doc.add(new TextField("crop", crop, Store.YES));
				String disease = res.getString("disease") == null ? "" : res.getString("disease");
				doc.add(new TextField("disease", disease, Store.YES));
				String volume = res.getString("volume") == null ? "" : res.getString("volume");
				doc.add(new TextField("volume", volume, Store.YES));
				String method = res.getString("method") == null ? "" : res.getString("method");
				doc.add(new TextField("method", method, Store.YES));
				String model = res.getString("model") == null ? "" : res.getString("model");
				doc.add(new TextField("model", model, Store.YES));

				iwriter.addDocument(doc);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (iwriter != null) {
			try {
				iwriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void createPetIndex() {
		Analyzer analyzer = new IKAnalyzer(true);
		Directory directory = null;
		IndexWriter iwriter = null;

		try {
			directory = FSDirectory.open(new File(this.path));
			iwriter = getIndexWriter(directory, analyzer);
		} catch (IOException e) {
			this.logger.error("open index file failed", e);
		}

		try {

			Connection con = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = (Connection) DriverManager.getConnection(
					"jdbc:MySQL://localhost:3306/ploughHelper?useUnicode=true&characterEncoding=UTF-8", "root",
					"weather123");
			Statement stmt;
			stmt = (Statement) con.createStatement();
			String selectSql = "SELECT * FROM petDisSpec";
			ResultSet res = stmt.executeQuery(selectSql);
			while (res.next()) {
				/*
				 * Document doc = new Document();
				 * 
				 * String petDisSpecId = res.getString("petDisSpecId");
				 * FieldType ft = new FieldType(); ft.setIndexed(false);
				 * ft.setStored(true); ft.setTokenized(false); doc.add(new
				 * Field("petDisSpecId", petDisSpecId, ft)); //id only need to
				 * store, no index, no 分词
				 * 
				 * String petDisSpecName = res.getString("petDisSpecName") ==
				 * null ? "" : res.getString("petDisSpecName"); doc.add(new
				 * TextField("petDisSpecName",petDisSpecName, Store.YES));
				 * 
				 * String type = res.getString("type"); FieldType typeFt = new
				 * FieldType(); typeFt.setIndexed(false);
				 * typeFt.setStored(true); typeFt.setTokenized(false);
				 * doc.add(new Field("type", type, typeFt));
				 * 
				 * String alias = res.getString("alias") == null ? "" :
				 * res.getString("alias"); doc.add(new TextField("alias", alias,
				 * Store.YES)); String cropName = res.getString("cropName") ==
				 * null ? "" : res.getString("cropName"); doc.add(new
				 * TextField("cropName", cropName, Store.YES)); String color =
				 * res.getString("color") == null ? "" : res.getString("color");
				 * doc.add(new TextField("color", color, Store.YES)); String
				 * shape = res.getString("shape") == null ? "" :
				 * res.getString("shape"); doc.add(new TextField("shape", shape,
				 * Store.YES)); String description =
				 * res.getString("description") == null ? "" :
				 * res.getString("description"); doc.add(new
				 * TextField("description", description, Store.YES)); String
				 * sympthon = res.getString("sympthon") == null ? "" :
				 * res.getString("sympthon"); doc.add(new TextField("sympthon",
				 * sympthon, Store.YES)); String forms = res.getString("forms")
				 * == null ? "" : res.getString("forms"); doc.add(new
				 * TextField("forms", forms, Store.YES)); String habits =
				 * res.getString("habits") == null ? "" :
				 * res.getString("habits"); doc.add(new TextField("habits",
				 * habits, Store.YES)); String rules = res.getString("rules") ==
				 * null ? "" : res.getString("rules"); doc.add(new
				 * TextField("rules", rules, Store.YES));
				 * 
				 * iwriter.addDocument(doc);
				 */

				Document doc = new Document();

				/*
				 * String varietyId = res.getString("varietyId"); FieldType ft =
				 * new FieldType(); ft.setIndexed(false); ft.setStored(true);
				 * ft.setTokenized(false); doc.add(new Field("varietyId",
				 * varietyId, ft)); //id only need to store, no index, no 鍒嗚瘝
				 * String varietyName = res.getString("varietyName") == null ?
				 * "" : res.getString("varietyName"); doc.add(new
				 * TextField("varietyName", varietyName, Store.YES)); String
				 * subId = res.getString("subId") == null ? "" :
				 * res.getString("subId"); doc.add(new TextField("subId", subId,
				 * Store.YES)); String registerationId =
				 * res.getString("registerationId") == null ? "" :
				 * res.getString("registerationId"); doc.add(new
				 * TextField("registerationId", registerationId, Store.YES));
				 * String varietySource = res.getString("varietySource") == null
				 * ? "" : res.getString("varietySource"); doc.add(new
				 * TextField("varietySource", varietySource, Store.YES)); String
				 * characteristics = res.getString("characteristics") == null ?
				 * "" : res.getString("characteristics"); doc.add(new
				 * TextField("characteristics", characteristics, Store.YES));
				 * String yieldPerformance = res.getString("yieldPerformance")
				 * == null ? "" : res.getString("yieldPerformance"); doc.add(new
				 * TextField("yieldPerformance", yieldPerformance, Store.YES));
				 * String suitableArea = res.getString("suitableArea") == null ?
				 * "" : res.getString("suitableArea"); doc.add(new
				 * TextField("suitableArea", suitableArea, Store.YES));
				 */

				String petDisSpecId = res.getString("petDisSpecId");
				FieldType ft = new FieldType();
				ft.setIndexed(false);
				ft.setStored(true);
				ft.setTokenized(false);
				doc.add(new Field("petDisSpecId", petDisSpecId, ft)); // id only
																		// need
																		// to
																		// store,
																		// no
																		// index,
																		// no
																		// 鍒嗚瘝

				String petDisSpecName = res.getString("petDisSpecName") == null ? "" : res.getString("petDisSpecName");
				doc.add(new TextField("petDisSpecName", petDisSpecName, Store.YES));

				String type = res.getString("type");
				FieldType typeFt = new FieldType();
				typeFt.setIndexed(false);
				typeFt.setStored(true);
				typeFt.setTokenized(false);
				doc.add(new Field("type", type, typeFt));

				String alias = res.getString("alias") == null ? "" : res.getString("alias");
				doc.add(new TextField("alias", alias, Store.YES));
				String cropName = res.getString("cropName") == null ? "" : res.getString("cropName");
				doc.add(new TextField("cropName", cropName, Store.YES));
				String color = res.getString("color") == null ? "" : res.getString("color");
				doc.add(new TextField("color", color, Store.YES));
				String shape = res.getString("shape") == null ? "" : res.getString("shape");
				doc.add(new TextField("shape", shape, Store.YES));
				String description = res.getString("description") == null ? "" : res.getString("description");
				doc.add(new TextField("description", description, Store.YES));
				String sympthon = res.getString("sympthon") == null ? "" : res.getString("sympthon");
				doc.add(new TextField("sympthon", sympthon, Store.YES));
				String forms = res.getString("forms") == null ? "" : res.getString("forms");
				doc.add(new TextField("forms", forms, Store.YES));
				String habits = res.getString("habits") == null ? "" : res.getString("habits");
				doc.add(new TextField("habits", habits, Store.YES));
				String rules = res.getString("rules") == null ? "" : res.getString("rules");
				doc.add(new TextField("rules", rules, Store.YES));

				iwriter.addDocument(doc);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (iwriter != null) {
			try {
				iwriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public HashMap[] search(String keyword, String[] searchFields, String[] resultFields)
			throws ParseException, IOException, InvalidTokenOffsetsException {
		HashMap[] result = new HashMap[NUM];

		Analyzer analyzer = new IKAnalyzer(true);
		Directory directory = null;
		IndexReader ireader = null;
		IndexSearcher isearcher = null;

		try {
			directory = FSDirectory.open(new File(this.path));
			ireader = IndexReader.open(directory);
		} catch (IOException e) {
			this.logger.error("search -- open index file failed", e);
		}
		isearcher = new IndexSearcher(ireader);

		QueryParser qp = new MultiFieldQueryParser(Version.LUCENE_46, searchFields, analyzer);
		qp.setDefaultOperator(QueryParser.AND_OPERATOR);
		Query query = qp.parse(keyword);

		TopDocs topDocs = isearcher.search(query, NUM);

		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++) {
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			result[i] = new HashMap();
			for (int j = 0; j < resultFields.length; j++) {
				if (resultFields[j].equals(this.tableName + "Id") || resultFields[j].equals(this.tableName + "Name")) {
					result[i].put(resultFields[j], targetDoc.get(resultFields[j]));
				} else if (this.highlightFormatter != null) {
					Highlighter highlighter = new Highlighter(this.highlightFormatter, new QueryScorer(query));
					highlighter.setTextFragmenter(new SimpleFragmenter(HIGHLIGHT_LEN));
					TokenStream tokenStream1 = analyzer.tokenStream(resultFields[j],
							new StringReader(targetDoc.get(resultFields[j])));
					String _result = highlighter.getBestFragment(tokenStream1, targetDoc.get(resultFields[j]));
					if (_result == null) { // not highlight the field which
											// doesn't contain the keyword
						_result = targetDoc.get(resultFields[j]);
					}
					result[i].put(resultFields[j], _result);
				} else {
					result[i].put(resultFields[j], targetDoc.get(resultFields[j]));
				}
			}
		}
		return result;
	}

	public void setHighlighterFormatter(String highLightPrefix, String highLightSuffix) {
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(highLightPrefix, highLightSuffix);
		this.highlightFormatter = simpleHTMLFormatter;
	}

	public static void main(String args[]) {
		FullTextSearch bs = null;
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			bs = new FullTextSearch("variety", "E:/index/varietyIndex");
		} else {

		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");
		// bs.createVarietyIndex(); // only need create index once
		String[] a = { "varietyName", "varietySource" };
		String[] b = { "varietyName", "varietyId", "varietySource" };
		try {
			HashMap[] h = bs.search("杭州", a, b);
			for (int k = 0; k < h.length; k++) {
				if (h[k] == null) {
					break;
				}
			}
		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			bs = new FullTextSearch("petDisSpec", "E:/index/petDisSpecIndex");
		} else {

		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");
		// bs.createPetIndex();
		// only need create index once

		HashMap result = new HashMap();
		String[] resource = { "petDisSpecName", "cropName", "sympthon" };
		String[] content = { "petDisSpecName", "petDisSpecId", "cropName", "sympthon", "type" };
		try {
			HashMap<String, String>[] h = bs.search("杭州", resource, content);
			List<HashMap<String, String>> ill = new ArrayList<HashMap<String, String>>();
			List<HashMap<String, String>> pest = new ArrayList<HashMap<String, String>>();
			List<HashMap<String, String>> grass = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < h.length; i++) {
				if (h[i] != null) {
					if (h[i].get("type").equals("病害"))
						ill.add(h[i]);
					if (h[i].get("type").equals("虫害"))
						pest.add(h[i]);
					if (h[i].get("type").equals("草害"))
						grass.add(h[i]);
				}
			}
			result.put("ill", ill);
			result.put("pest", pest);
			result.put("grass", grass);
		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}

		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			bs = new FullTextSearch("petDisSpec", "E:/index/cPProductIndex");
		} else {

		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");
		// bs.createPetIndex();
		// bs.createCpproductIndex();
		// only need create index once

		try {
			String[] a1 = { "cPProductName", "activeIngredient", "manufacturer", "crop" };
			String[] b1 = { "cPProductName", "activeIngredient", "manufacturer", "crop", "cPProductId" };

			HashMap<String, String>[] h = bs.search("玉米", a1, b1);
			for (int k = 0; k < h.length; k++) {
				if (h[k] == null) {
					break;
				}
			}

		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}

	}
}
