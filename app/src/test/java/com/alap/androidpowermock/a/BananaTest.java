package com.alap.androidpowermock.a;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class BananaTest {

    private static final String TAG = "Ban";

    //测试静态static方法
    @Test
    @PrepareForTest({Banana.class})
    public void testVoid() {
        PowerMockito.mockStatic(Banana.class); //<-- mock静态类
        Mockito.when(Banana.getColor()).thenReturn("绿色");
        Assert.assertEquals("绿色", Banana.getColor());
        System.out.println("测试静态static方法 " + Banana.getColor());
    }

    //更改类的私有static常量，修改Banana中的color
    @Test
    @PrepareForTest({Banana.class})
    public void testChangColor() {
        Whitebox.setInternalState(Banana.class, "COLOR", "红色的");
        Assert.assertEquals("红色的", Banana.getColor());
        System.out.println("更改类的私有static常量，修改Banana中的color " + Banana.getColor());
    }


    //mock私有方法
    @Test
    @PrepareForTest({Banana.class})
    public void testPrivate() throws Exception {
        Banana banana = PowerMockito.mock(Banana.class);
        PowerMockito.when(banana.getBananaInfo()).thenCallRealMethod();
        PowerMockito.when(banana, "flavor").thenReturn("齁咸啊");
        Assert.assertEquals("齁咸啊黄色的", banana.getBananaInfo());
        //验证flavor是否调用一次
        PowerMockito.verifyPrivate(banana).invoke("flavor");
        System.out.println("mock私有方法 " + banana.getBananaInfo());
    }

    //通过mock私有方法flavor，使得之前的“甜甜的”变为了“苦苦的”。当然我们也可以跳过私有方法，代码如下
    @Test
    @PrepareForTest({Banana.class})
    public void skipPrivate() {
        Banana aBanana = new Banana();
        //跳过flavor方法
        PowerMockito.suppress(PowerMockito.method(Banana.class, "flavor"));
        Assert.assertEquals("null黄色的", aBanana.getBananaInfo());
        System.out.println("跳过私有方法 " + aBanana.getBananaInfo());

    }

    //更改父类私有变量，比如修改Fruit中的fruit,不能是final
    @Test
    @PrepareForTest({Banana.class})
    public void testChangeParentPrivate() throws Exception {
        Banana bBanana = new Banana();
        MemberModifier.field(Banana.class, "fruit").set(bBanana, "蔬菜");
        Assert.assertEquals("蔬菜", bBanana.getFruit());
        System.out.println("更改父类Fruit私有变量，比如修改Fruit中的fruit不能是final " + bBanana.getFruit());
    }

    //MOCK FINAL方法
    @Test
    @PrepareForTest({Banana.class})
    public void testFinalMethod() throws Exception {
        Banana banana = PowerMockito.mock(Banana.class);
        PowerMockito.when(banana.isLike()).thenReturn(false);
        Assert.assertFalse(banana.isLike());
        System.out.println("MOCK FINAL方法 " + banana.isLike());
    }

    //mock构造方法
    @Test
    @PrepareForTest({Banana.class})
    public void testNewClass() throws Exception {
        Banana banana = PowerMockito.mock(Banana.class);
        PowerMockito.when(banana.getBananaInfo()).thenReturn("大香蕉？");
        //如果new新对象，则返回这个上面设置的对象
        PowerMockito.whenNew(Banana.class).withNoArguments().thenReturn(banana);
    //new 新的对象
        Banana newBanana = new Banana();
        Assert.assertEquals("大香蕉？",newBanana.getBananaInfo());

        //whenNew 方法的意思是之后 new 这个对象时，返回某个被 Mock 的对象而不是让真的 new 新的对象。
        // 如果构造方法有参数，可以在withNoArguments方法中传入。
        System.out.println("mock构造方法 "+newBanana.getBananaInfo());
    }

    //其他

}
