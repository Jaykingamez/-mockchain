import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages({"beans", "dao", "selenium"})
@Suite
public class IndexSuite {
	
}