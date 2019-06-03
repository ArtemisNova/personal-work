package com.jUnitPractice.junit.helper;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ StringHelperParameterisedTest.class, StringHelperTest.class })
public class StringHelperTestSuite {

}
 