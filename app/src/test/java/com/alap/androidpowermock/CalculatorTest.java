package com.alap.androidpowermock;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Calculator.class})
public class CalculatorTest {
    @Test
    public void testIntDivide() {
        Calculator calculator = Mockito.spy(Calculator.class);
        calculator.intDivide(1, 0);
    }

    @Test
    public void testPrivateIntDivideMethod() throws Exception {
        Calculator calculatorPowermockObj = PowerMockito.spy(new Calculator());
        calculatorPowermockObj.intDivide(1, 0);
        PowerMockito.verifyPrivate(calculatorPowermockObj, times(1)).invoke("dealZeroCase"); // Pass
    }
}