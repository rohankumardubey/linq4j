/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package net.hydromatic.linq4j.expressions;

import java.lang.reflect.Type;

/**
 * Represents an expression that has a binary operator.
 */
public class BinaryExpression extends Expression {
    public final Expression expression0;
    public final Expression expression1;
    private final Primitive primitive;

    BinaryExpression(
        ExpressionType nodeType,
        Type type,
        Expression expression0,
        Expression expression1)
    {
        super(nodeType, type);
        this.expression0 = expression0;
        this.expression1 = expression1;
        this.primitive = deducePrimitive();
    }

    private Primitive deducePrimitive() {
        if (expression0.getType() == Integer.TYPE) {
            return Primitive.INT;
        }
        if (expression0.getType() == Double.TYPE) {
            return Primitive.DOUBLE;
        }
        if (expression0.getType() == Boolean.TYPE) {
            return Primitive.BOOLEAN;
        }
        return Primitive.OTHER;
    }

    public Object evaluate(Evaluator evaluator) {
        switch (nodeType) {
        case AndAlso:
            return (Boolean) expression0.evaluate(evaluator)
                && (Boolean) expression1.evaluate(evaluator);
        case Add:
            switch (primitive) {
            case INT:
                return (Integer) expression0.evaluate(evaluator)
                    + (Integer) expression1.evaluate(evaluator);
            case DOUBLE:
                return (Double) expression0.evaluate(evaluator)
                    + (Double) expression1.evaluate(evaluator);
            default:
                throw cannotEvaluate();
            }
        case Divide:
            switch (primitive) {
            case INT:
                return (Integer) expression0.evaluate(evaluator)
                       / (Integer) expression1.evaluate(evaluator);
            case DOUBLE:
                return (Double) expression0.evaluate(evaluator)
                       / (Double) expression1.evaluate(evaluator);
            default:
                throw cannotEvaluate();
            }
        case Equal:
            return expression0.evaluate(evaluator)
                .equals(expression1.evaluate(evaluator));
        case GreaterThan:
            switch (primitive) {
            case INT:
                return (Integer) expression0.evaluate(evaluator)
                       > (Integer) expression1.evaluate(evaluator);
            case DOUBLE:
                return (Double) expression0.evaluate(evaluator)
                       > (Double) expression1.evaluate(evaluator);
            default:
                throw cannotEvaluate();
            }
        case GreaterThanOrEqual:
            switch (primitive) {
            case INT:
                return (Integer) expression0.evaluate(evaluator)
                       >= (Integer) expression1.evaluate(evaluator);
            case DOUBLE:
                return (Double) expression0.evaluate(evaluator)
                       >= (Double) expression1.evaluate(evaluator);
            default:
                throw cannotEvaluate();
            }
        case LessThan:
            switch (primitive) {
            case INT:
                return (Integer) expression0.evaluate(evaluator)
                       < (Integer) expression1.evaluate(evaluator);
            case DOUBLE:
                return (Double) expression0.evaluate(evaluator)
                       < (Double) expression1.evaluate(evaluator);
            default:
                throw cannotEvaluate();
            }
        case LessThanOrEqual:
            switch (primitive) {
            case INT:
                return (Integer) expression0.evaluate(evaluator)
                       <= (Integer) expression1.evaluate(evaluator);
            case DOUBLE:
                return (Double) expression0.evaluate(evaluator)
                       <= (Double) expression1.evaluate(evaluator);
            default:
                throw cannotEvaluate();
            }
        case Multiply:
            switch (primitive) {
            case INT:
                return (Integer) expression0.evaluate(evaluator)
                       * (Integer) expression1.evaluate(evaluator);
            case DOUBLE:
                return (Double) expression0.evaluate(evaluator)
                       * (Double) expression1.evaluate(evaluator);
            default:
                throw cannotEvaluate();
            }
        case NotEqual:
            return !expression0.evaluate(evaluator)
                .equals(expression1.evaluate(evaluator));
        case OrElse:
            return (Boolean) expression0.evaluate(evaluator)
                   || (Boolean) expression1.evaluate(evaluator);
        case Subtract:
            switch (primitive) {
            case INT:
                return (Integer) expression0.evaluate(evaluator)
                       - (Integer) expression1.evaluate(evaluator);
            case DOUBLE:
                return (Double) expression0.evaluate(evaluator)
                       - (Double) expression1.evaluate(evaluator);
            default:
                throw cannotEvaluate();
            }
        default:
            throw cannotEvaluate();
        }
    }

    void accept(ExpressionWriter writer, int lprec, int rprec) {
        if (writer.requireParentheses(this, lprec, rprec)) {
            return;
        }
        expression0.accept(writer, lprec, nodeType.lprec);
        writer.append(nodeType.op);
        expression1.accept(writer, nodeType.rprec, rprec);
    }

    private RuntimeException cannotEvaluate() {
        return new RuntimeException(
            "cannot evaluate " + this
            + ", nodeType=" + nodeType
            + ", primitive=" + primitive);
    }

    enum Primitive {
        INT,
        DOUBLE,
        BOOLEAN,
        OTHER
    }
}

// End BinaryExpression.java
