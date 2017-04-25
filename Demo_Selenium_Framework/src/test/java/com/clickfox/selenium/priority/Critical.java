package com.clickfox.selenium.priority;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;


/**
 * @author yared
 *
 */
@RunWith(Categories.class)
@Categories.IncludeCategory({Critical.class})
public interface Critical {
}
