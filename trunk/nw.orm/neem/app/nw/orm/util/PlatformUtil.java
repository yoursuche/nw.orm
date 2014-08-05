package nw.orm.util;

import java.io.File;

public class PlatformUtil {


	/**
	 * 
	 * @return true if file system supports case sensitive naming
	 */
	public static boolean isFileSystemCaseSensitive(){
		return !new File("z").equals(new File("Z"));
	}
}
