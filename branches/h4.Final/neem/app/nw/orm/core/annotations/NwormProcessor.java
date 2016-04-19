package nw.orm.core.annotations;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes({"nw.orm.core.annotations.NwormTransaction"})
public class NwormProcessor extends AbstractProcessor{

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		Messager msgr = processingEnv.getMessager();
		for (TypeElement te : annotations) {
			for(Element e: env.getElementsAnnotatedWith(te)){
				
				msgr.printMessage(Kind.NOTE, e.getSimpleName());
				System.out.println("Totem Nasu si");
			}
		}
		return true;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> types = new HashSet<String>();
		types.add("nw.orm.core.annotations.NwormTransaction");
		return types;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}


}
