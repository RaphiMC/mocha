package team.unnamed.molang.expression.binary;

import team.unnamed.molang.context.EvalContext;
import team.unnamed.molang.expression.Expression;

/**
 * {@link BinaryExpression} implementation for
 * representing field accessing
 */
public class AccessExpression
        extends BinaryExpression {

    public AccessExpression(
            Expression leftHand,
            Expression rightHand
    ) {
        super(leftHand, rightHand);
    }

    @Override
    public Object eval(EvalContext context) {
        return leftHand.evalProperty(context, rightHand); // temporary
    }

    @Override
    public String toString() {
        return leftHand + "." + rightHand;
    }
}
