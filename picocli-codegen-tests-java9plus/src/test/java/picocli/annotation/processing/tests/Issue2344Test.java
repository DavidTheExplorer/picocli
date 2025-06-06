package picocli.annotation.processing.tests;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.annotation.processing.Processor;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

public class Issue2344Test
{
    //@Ignore("https://github.com/remkop/picocli/issues/2344")
    @Test
    public void testIssue2344() {
        Processor processor = new AnnotatedCommandSourceGeneratorProcessor();
        Compilation compilation =
            javac()
                .withProcessors(processor)
                .compile(JavaFileObjects.forResource(
                    "picocli/issue2344/Application.java"));

        assertThat(compilation).succeeded();
    }
}
