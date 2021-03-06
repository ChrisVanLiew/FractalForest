package com.eclipsekingdom.fractalforest.util.theme;

import com.eclipsekingdom.fractalforest.util.theme.type.*;

public enum ThemeType {
    OAK(new OakTheme()),
    GOLDEN(new GoldenTheme()),
    BIRCH(new BirchTheme()),
    FALL_BIRCH(new FallBirchTheme()),
    FALL_OAK(new FallOakTheme()),
    FALL_ELM(new FallElmTheme()),
    FALL_MAPLE(new FallMapleTheme()),
    FLOWERING_HAWTHORN(new FloweringHawthornTheme()),
    WEIRWOOD(new WeirwoodTheme()),
    FLAME_TREE(new FlameTheme()),
    BLOOD_BUSH(new BloodBush()),
    WHITE_ASH(new WhiteAshTheme()),
    CYGNI(new CygniTheme()),
    SECCHI(new SecchiTheme());

    private Theme theme;

    ThemeType(ITheme theme) {
        this.theme = new Theme(theme);
    }

    public ITheme getTheme() {
        return theme;
    }


}
