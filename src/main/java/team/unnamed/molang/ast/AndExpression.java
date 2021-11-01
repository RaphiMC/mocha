package team.unnamed.molang.ast;

import team.unnamed.molang.context.EvalContext;

/**
 * A logical "AND" expression, with a left-hand
 * and right-hand expressions that will be compared
 * to get the final result
 */
public class AndExpression
        extends InfixExpression {

    public AndExpression(Expression leftHand, Expression rightHand) {
        super(leftHand, rightHand);
    }

    @Override
    public Object eval(EvalContext context) {
        return leftHand.evalAsBoolean(context)
                && rightHand.evalAsBoolean(context);
    }

    @Override
    public String toSource() {
        return leftHand.toSource() + " && " + rightHand.toSource();
    }

}
