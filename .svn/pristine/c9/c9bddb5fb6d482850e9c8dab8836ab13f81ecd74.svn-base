package com.hncis.common.ibatis;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Node;

import com.ibatis.common.xml.Nodelet;
import com.ibatis.common.xml.NodeletParser;
import com.ibatis.common.xml.NodeletUtils;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapClasspathEntityResolver;

//import com.ibatis.sqlmap.engine.builder.xml.XmlParserState;

@SuppressWarnings("unchecked")
public class SqlMapExtractingSqlMapConfigParser {

	protected final NodeletParser parser = new NodeletParser();
	// private XmlParserState state = new XmlParserState();

	private List sqlMapList = new ArrayList();

	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	public SqlMapExtractingSqlMapConfigParser() {
		parser.setValidation(true);
		parser.setEntityResolver(new SqlMapClasspathEntityResolver());

		addSqlMapNodelets();

	}

	public List parse(Reader reader) {
		try {
			parser.parse(reader);
			return sqlMapList;
		} catch (Exception e) {
			throw new RuntimeException("Error occurred.  Cause: " + e, e);
		}
	}

	public List parse(InputStream inputStream) {
		try {
			parser.parse(inputStream);
			return sqlMapList;
		} catch (Exception e) {
			throw new RuntimeException("Error occurred.  Cause: " + e, e);
		}
	}

	protected void addSqlMapNodelets() {
		parser.addNodelet("/sqlMapConfig/sqlMap", new Nodelet() {
			public void process(Node node) throws Exception {
				// state.getConfig().getErrorContext().setActivity(
				// "loading the SQL Map resource");

				Properties attributes = NodeletUtils.parseAttributes(node,
				// state.getGlobalProps());
						null);

				String resource = attributes.getProperty("resource");
				String url = attributes.getProperty("url");

				if (resource != null) {
					sqlMapList.add(resourceLoader.getResource(resource));
				} else if (url != null) {
					sqlMapList.add(resourceLoader.getResource(url));
				} else {
					throw new SqlMapException(
							"The <sqlMap> element requires either a resource or a url attribute.");
				}
			}
		});
	}

}
