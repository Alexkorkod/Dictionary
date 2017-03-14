package com.stemlocal.app;

import ru.stachek66.nlp.mystem.holding.Factory;
import ru.stachek66.nlp.mystem.holding.MyStem;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;
import ru.stachek66.nlp.mystem.holding.Request;
import ru.stachek66.nlp.mystem.model.Info;
import scala.Option;
import scala.collection.JavaConversions;

import java.io.File;

public class MyStemLocal {

    private final static MyStem mystemAnalyzer =
            new Factory("-igd --eng-gr --format json --weight")
                    .newMyStem("3.0", Option.<File>empty()).get();

    public static void main(final String[] args) throws MyStemApplicationException {

        Iterable<Info> result =
                JavaConversions.asJavaIterable(
                        mystemAnalyzer
                                //.analyze(Request.apply("И вырвал грешный мой язык"))
                                .analyze(Request.apply("Эйнштейн использовав квантовую механику"))
                                .info()
                                .toIterable());

        for (Info info : result) {
            System.out.println(info.initial() + " -> " + info.lex() + " | " + info.rawResponse());
        }
    }
}