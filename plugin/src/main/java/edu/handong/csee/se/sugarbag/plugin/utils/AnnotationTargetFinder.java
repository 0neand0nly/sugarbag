package edu.handong.csee.se.sugarbag.plugin.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

/**
 * Class that provides methods to find annotation targets.
 */
public class AnnotationTargetFinder {

    /**
     * Finds <code>VariableTree</code>s that have the given annotation 
     * and identifier type from the given trees.
     * @param trees the trees
     * @param annotationName the annotation name
     * @param identifierType the identifier type name
     * @return the <code>VariableTree</code>s if they exists, 
     *         <code>null</code> otherwise
     */
    public static List<Tree> findTargetVariables(
            List<? extends Tree> trees, String annotationName, 
            String identifierType) {
        return trees.stream()
                    .filter(t -> t.getKind() == Tree.Kind.VARIABLE 
                                 && checkVariable((VariableTree) t, 
                                                  annotationName, 
                                                  identifierType))
                    .collect(Collectors.toList());
    }

    /**
     * Checks whether the given class has the given annotation or not.
     * @param clazz the class tree
     * @param annotationName the annotation name
     * @return <code>true</code> if the given class has the given annotation,
     *         <code>false</code> otherwise
     */
    private static boolean checkClass(ClassTree clazz, String annotationName) {
        return clazz.getModifiers()
                    .getAnnotations()
                    .stream()
                    .anyMatch(t -> t.getAnnotationType()
                                    .toString()
                                    .equals(annotationName));
    }

    /**
     * Checks whether the given method has the given annotation or not.
     * @param method the method tree
     * @param annotationName the annotation name
     * @return <code>true</code> if the given method has the given annotation,
     *         <code>false</code> otherwise
     */
    private static boolean checkMethod(MethodTree method, 
                                       String annotationName) {
        return method.getModifiers()
                     .getAnnotations()
                     .stream()
                     .anyMatch(t -> t.getAnnotationType()
                                     .toString()
                                     .equals(annotationName));
    }

    /**
     * Checks whether the given variable has the given annotation 
     * and identifier type or not. If the identifier type is <code>null</code>, 
     * it does not care about the identifier type.
     * @param variable the variable tree
     * @param annotationName the annotation name
     * @param identifierType the identifier type name
     * @return <code>true</code> if the given variable has the given annotation,
     *         <code>false</code> otherwise
     */
    private static boolean checkVariable(VariableTree variable, 
                                         String annotationName, 
                                         String identifierType) {
        return variable.getModifiers()
                       .getAnnotations()
                       .stream()
                       .anyMatch(t -> t.getAnnotationType()
                                       .toString()
                                       .equals(annotationName))
               && (identifierType == null || variable.getType()
                                                     .toString()
                                                     .equals(identifierType));
    }
}
