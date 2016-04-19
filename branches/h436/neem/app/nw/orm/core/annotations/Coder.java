package nw.orm.core.annotations;

import java.lang.annotation.Documented;

@Documented
public @interface Coder {

	String author();

	String date();

	int currentRevision() default 1;

	String lastModified() default "N/A";

	String lastModifiedBy() default "N/A";

	String[] reviewers() default {""};

}
