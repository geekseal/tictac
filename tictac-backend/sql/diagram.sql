-- MySQL Script generated by MySQL Workbench
-- Tue Nov 21 15:32:39 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema finaldb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema finaldb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `finaldb` DEFAULT CHARACTER SET utf8 ;
USE `finaldb` ;

DROP TABLE IF EXISTS `finaldb`.`Video`;
DROP TABLE IF EXISTS `finaldb`.`mybookmarklist`;
DROP TABLE IF EXISTS `finaldb`.`Comment`;
DROP TABLE IF EXISTS `finaldb`.`User`;

-- -----------------------------------------------------
-- Table `finaldb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finaldb`.`User` (
  `userId` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `email` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finaldb`.`Video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finaldb`.`Video` (
  `videoId` INT NOT NULL AUTO_INCREMENT,
  `User_userId` VARCHAR(20) NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  `videoSrc` VARCHAR(200) NOT NULL,
  `regDate` DATE NOT NULL,
  `heartCnt` INT NOT NULL DEFAULT 0,
  `bookmarkCnt` INT NOT NULL DEFAULT 0,
  `viewCnt` INT NOT NULL DEFAULT 0,
  `thumbnailImgSrc` VARCHAR(200) NOT NULL,
  `categoryId` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`videoId`),
  INDEX `fk_Video_User_idx` (`User_userId` ASC) VISIBLE,
  CONSTRAINT `fk_Video_User`
    FOREIGN KEY (`User_userId`)
    REFERENCES `finaldb`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
ALTER TABLE Video AUTO_INCREMENT=10000;

-- -----------------------------------------------------
-- Table `finaldb`.`MyBookmarkList`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finaldb`.`MyBookmarkList` (
  `User_userId` VARCHAR(20) NOT NULL,
  `Video_videoId` INT NOT NULL,
  INDEX `fk_MyBookmarkList_User1_idx` (`User_userId` ASC) VISIBLE,
  INDEX `fk_MyBookmarkList_Video1_idx` (`Video_videoId` ASC) VISIBLE,
  CONSTRAINT `fk_MyBookmarkList_User1`
    FOREIGN KEY (`User_userId`)
    REFERENCES `finaldb`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MyBookmarkList_Video1`
    FOREIGN KEY (`Video_videoId`)
    REFERENCES `finaldb`.`Video` (`videoId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `finaldb`.`Comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `finaldb`.`Comment` (
  `Video_videoId` INT NOT NULL,
  `commentId` INT NOT NULL AUTO_INCREMENT,
  `User_userId` VARCHAR(20) NOT NULL,
  `User_username` VARCHAR(20) NOT NULL,
  `content` VARCHAR(200) NOT NULL,
  `regDate` DATE NOT NULL,
  INDEX `fk_Comment_Video1_idx` (`Video_videoId` ASC) INVISIBLE,
  INDEX `fk_Comment_User1_idx` (`User_username` ASC) VISIBLE,
  PRIMARY KEY (`commentId`),
  INDEX `fk_Comment_User2_idx` (`User_userId` ASC) VISIBLE,
  CONSTRAINT `fk_Comment_Video1`
    FOREIGN KEY (`Video_videoId`)
    REFERENCES `finaldb`.`Video` (`videoId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comment_User1`
    FOREIGN KEY (`User_username`)
    REFERENCES `finaldb`.`User` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comment_User2`
    FOREIGN KEY (`User_userId`)
    REFERENCES `finaldb`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
