package com.Model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
// no args constructor is so important!
@NoArgsConstructor(force = true)
public class Cat {
    private int id;
    private final String name;
    private final String gender;
    private final boolean longHaired;
    private final boolean roundFace;
    private final boolean lively;
    private final String picPath;
    private final String thPicPath;

    public Cat(int id, String name, String gender, boolean longHaired, boolean roundFace,
               boolean lively, String picPath, String thPicPath) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.longHaired = longHaired;
        this.roundFace = roundFace;
        this.lively = lively;
        this.picPath = picPath;
        this.thPicPath = thPicPath;
    }
}
