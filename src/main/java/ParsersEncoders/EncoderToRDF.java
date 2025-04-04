package ParsersEncoders;

import java.util.ArrayList;

public class EncoderToRDF {
	
	
	
	public String getHeader(String Package) {
		
		String header = "<rdf:RDF\r\n"
				+ "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n"
				+ "    xmlns=\"http://www.w3.org/TR/html4/\"\r\n"
				+ "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n"
				+ "    xmlns:j.0=\"http://acandonorway.github.com/XmlToRdf/ontology.ttl#\">\r\n"
				+ "  <BusinessObject>\r\n"
				+ "    <Version></Version>\r\n"
				+ "    <Model_Name>"+Package+"</Model_Name>\r\n"
				+ "    <Model_Description></Model_Description>\r\n"
				+ "    <Model_Creator></Model_Creator>\r\n"
				+ "    <DateCreated>06/12/2021</DateCreated>\r\n"
				+ "    <Comments></Comments>\r\n"
				+ "    <j.0:hasChild>";
		return header;
	}
	
	
	
	
	public String getClassHeader() {
		
		String cl =  "<Classes>     \r\n"
					+ "     <j.0:hasChild>  \r\n"
					+ "	<Class>\r\n"
					+ "            <Stereotype>Class</Stereotype>\r\n"
					+ "            <j.0:hasChild>\r\n"
					+ "            <Attributes>\r\n";
		
		return cl;
}
	
	
	public String getAttributes(String attr, String datatype) {
		
		String attributes = "    <j.0:hasChild>\r\n"
				+ "                  <Attribute>\r\n"
				+ "                    <DataType> "+datatype+"</DataType>\r\n"
				+ "                    <Setter></Setter>\r\n"
				+ "                    <CustomValidation></CustomValidation>\r\n"
				+ "                    <MinLength>0</MinLength>\r\n"
				+ "                    <Length>100</Length>\r\n"
				+ "                    <Description></Description>\r\n"
				+ "                    <IsRequired>false</IsRequired>\r\n"
				+ "                    <ReadOnly>false</ReadOnly>\r\n"
				+ "                    <IsEmail>false</IsEmail>\r\n"
				+ "                    <MaxLength>100</MaxLength>\r\n"
				+ "                    <Getter></Getter>\r\n"
				+ "                    <Persisted>true</Persisted>\r\n"
				+ "                    <Scale>2</Scale>\r\n"
				+ "                    <BaseInfo></BaseInfo>\r\n"
				+ "                    <IsStatic>false</IsStatic>\r\n"
				+ "                    <IsURL>false</IsURL>\r\n"
				+ "                    <MaxValue></MaxValue>\r\n"
				+ "                    <Name>"+attr+"</Name>\r\n"
				+ "                    <IsEncrypted>false</IsEncrypted>\r\n"
				+ "                    <Precision>8</Precision>\r\n"
				+ "                    <IsExternal>false</IsExternal>\r\n"
				+ "                    <IsInherited>false</IsInherited>\r\n"
				+ "                    <MinValue></MinValue>\r\n"
				+ "                    <InitValue></InitValue>\r\n"
				+ "                    <ColumnName></ColumnName>\r\n"
				+ "                    <IsValueClass>false</IsValueClass>\r\n"
				+ "                    <IsCreditCard>false</IsCreditCard>\r\n"
				+ "                  </Attribute>\r\n"
				+ "                </j.0:hasChild>\r\n";
		
		
		return attributes;
	}
	


	public String getClassBody(String modelName, String clName) {
								
				String cl = "      <BaseModel></BaseModel>\r\n"
					+ "            <ModelName>"+modelName+"</ModelName>\r\n"
					+ "            <IsStatic>false</IsStatic>\r\n"
					+ "            <TableName></TableName>\r\n"
					+ "            <Name>"+clName+"</Name>\r\n"
					+ "            <ConcurencyControl>true</ConcurencyControl>\r\n"
					+ "            <Description></Description>\r\n"
					+ "            <ShadowModel></ShadowModel>\r\n"
					+ "            <IsPersisted>true</IsPersisted>\r\n"
					+ "            <ShadowClass></ShadowClass>\r\n"
					+ "            <PK>Id</PK>\r\n"
					+ "            <BaseClasses></BaseClasses>\r\n"
					+ "            <BaseClass></BaseClass>\r\n"
					+ "            <IdGeneratorType>HiLoGenerator</IdGeneratorType>\r\n"
					+ "            <j.0:hasChild>\r\n"
					+ "              <DiagramInfo>\r\n"
					+ "                <Width>180</Width>\r\n"
					+ "                <Top>1</Top>\r\n"
					+ "                <Left>303</Left>\r\n"
					+ "                <IsExpanded>true</IsExpanded>\r\n"
					+ "                <Height>190</Height>\r\n"
					+ "              </DiagramInfo>\r\n"
					+ "            </j.0:hasChild>\r\n"
					+ "            <AutoAssignPrimaryKey>true</AutoAssignPrimaryKey>\r\n"
					+ "          </Class>\r\n"
					+ "        </j.0:hasChild>\r\n"
					+ "      </Classes>"
					+ "     </j.0:hasChild>";
		
		
		return cl;
	}
	
	
	
	public String getAssocHeader() {
		
		return "  <j.0:hasChild>\r\n "
				+ " <Associations>\r\n";
	}
	
	
	public String getAssociation(String modelName, String cl1, String cl2) {
	
		String assoc = "   <j.0:hasChild>\r\n  "
				+ "			 <Association>\r\n"
				+ "            <OnDelete2>CascadeDelete</OnDelete2>\r\n"
				+ "            <ModelName>"+modelName+"</ModelName>\r\n"
				+ "            <Multiplicity2>Many</Multiplicity2>\r\n"
				+ "            <Persisted>true</Persisted>\r\n"
				+ "            <OrderByDirection1>Asc</OrderByDirection1>\r\n"
				+ "            <Role2>"+cl2+"</Role2>\r\n"
				+ "            <IsInherited>false</IsInherited>\r\n"
				+ "            <Class2>"+cl2+"</Class2>\r\n"
				+ "            <IsExternal>false</IsExternal>\r\n"
				+ "            <UnlimitedFileSize>true</UnlimitedFileSize>\r\n"
				+ "            <OrderByDirection2>Asc</OrderByDirection2>\r\n"
				+ "            <Multiplicity1>ZeroOrOne</Multiplicity1>\r\n"
				+ "            <Navigable1>false</Navigable1>\r\n"
				+ "            <Class1>"+cl1+"</Class1>\r\n"
				+ "            <OrderByProperty2></OrderByProperty2>\r\n"
				+ "            <OnDelete1>Dissasociate</OnDelete1>\r\n"
				+ "            <IsShadow>false</IsShadow>\r\n"
				+ "            <OrderByProperty1></OrderByProperty1>\r\n"
				+ "            <Role1>"+cl1+"</Role1>\r\n"
				+ "            <AllowedExtensions></AllowedExtensions>\r\n"
				+ "            <StorageMedium>FileSystem</StorageMedium>\r\n"
				+ "            <MaxFileSize>1000</MaxFileSize>\r\n"
				+ "            <j.0:hasChild>\r\n"
				+ "              <DiagramInfo>\r\n"
				+ "                <Percent2>0.3025210084033613</Percent2>\r\n"
				+ "                <Percent1>0.38333333333333336</Percent1>\r\n"
				+ "                <Path>M 448 302 L 370 302 L 370 262 L 295 262</Path>\r\n"
				+ "                <Edge2>Right</Edge2>\r\n"
				+ "                <Edge1>Left</Edge1>\r\n"
				+ "              </DiagramInfo>\r\n"
				+ "            </j.0:hasChild>\r\n"
				+ "            <Navigable2>true</Navigable2>\r\n"
				+ "            <AllowAllExtensions>true</AllowAllExtensions>\r\n"
				+ "          </Association>\r\n"
				+ "        </j.0:hasChild>\r\n";
		
		return assoc;
	}
	
	
	public String getAssociFooter() {
		
		return "</Associations>";
	}
	
	public String getFooter() {
		return "    </j.0:hasChild>\r\n"
				+ "  </BusinessObject>\r\n"
				+ "</rdf:RDF>";
	}
	

}
