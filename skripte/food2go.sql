-- MySQL Script generated by MySQL Workbench
-- Thu Nov  7 19:13:56 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema id11412390_webdip2017x141
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema id11412390_webdip2017x141
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `id11412390_webdip2017x141` DEFAULT CHARACTER SET utf8 ;
USE `id11412390_webdip2017x141` ;

-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`Tip_korisnika`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `id11412390_webdip2017x141`.`Tip_korisnika` ;

CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`Tip_korisnika` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(100) NOT NULL,
  `Opis` VARCHAR(100) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`Korisnik`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`Korisnik` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Prezime` VARCHAR(45) NOT NULL,
  `KorisnickoIme` VARCHAR(45) NOT NULL,
  `Lozinka` VARCHAR(225) NOT NULL,
  `OIB` VARCHAR(15) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Adresa` VARCHAR(45) NOT NULL,
  `BrojMobitela` VARCHAR(45) NOT NULL,
  `BrojPokusaja` INT NOT NULL,
  `StatusKorisnika` INT ,
  `Aktivacijski_kod` VARCHAR(225) NULL,
  `Broj_bodova` INT NULL,
  `Tip_korisnikaID` INT NOT NULL,
  UNIQUE INDEX `Username_UNIQUE` (`KorisnickoIme` ASC),
  UNIQUE INDEX `OIB_UNIQUE` (`OIB` ASC),
  PRIMARY KEY (`ID`),
  INDEX `fk_Korisnik_Tip_korisnika_idx` (`Tip_korisnikaID` ASC),
  CONSTRAINT `fk_Korisnik_Tip_korisnika`
    FOREIGN KEY (`Tip_korisnikaID`)
    REFERENCES `id11412390_webdip2017x141`.`Tip_korisnika` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`Kategorija`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`Kategorija` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`Artikl`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`Artikl` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `Cijena` DOUBLE NOT NULL,
  `KolicinaZaliha` INT NOT NULL,
  `MinimalnaKolicina` INT NOT NULL,
  `Opis` VARCHAR(225) NULL,
  `KategorijaID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Artikl_Kategorija1_idx` (`KategorijaID` ASC) ,
  CONSTRAINT `fk_Artikl_Kategorija1`
    FOREIGN KEY (`KategorijaID`)
    REFERENCES `id11412390_webdip2017x141`.`Kategorija` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`StatusNarudzbe`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`StatusNarudzbe` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`Restoran`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`Restoran` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `Adresa` VARCHAR(45) NOT NULL,
  `IBAN` VARCHAR(45) NOT NULL,
  `OIB` VARCHAR(45) NOT NULL,
  `GodinaOsnivanja` INT NOT NULL,
  `KorisnikID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `OIB_UNIQUE` (`OIB` ASC),
  INDEX `fk_Restoran_Korisnik1_idx` (`KorisnikID` ASC),
  CONSTRAINT `fk_Restoran_Korisnik1`
    FOREIGN KEY (`KorisnikID`)
    REFERENCES `id11412390_webdip2017x141`.`Korisnik` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`PovratnaInformacija`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`PovratnaInformacija` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Komentar` VARCHAR(225) NOT NULL,
  `Ocjena` INT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`Racun`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`Racun` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `BrojRacuna` INT NOT NULL,
  `Ukupno` DOUBLE NOT NULL,
  `QRkod` VARCHAR(225) NOT NULL,
  `Datum` DATETIME NOT NULL,
  `Popust` INT NOT NULL,
  `KorisnikID` INT NOT NULL,
  `IskoristenQRKod` INT NULL,
  `Status_narudžbeID` INT NOT NULL,
  `RestoranID` INT NOT NULL,
  `Povratna_informacijaID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Racun_Korisnik1_idx` (`KorisnikID` ASC),
  INDEX `fk_Racun_StatusNarudzbe1_idx` (`Status_narudžbeID` ASC),
  INDEX `fk_Racun_Restoran1_idx` (`RestoranID` ASC),
  INDEX `fk_Racun_PovratnaInformacija2_idx` (`Povratna_informacijaID` ASC),
  CONSTRAINT `fk_Racun_Korisnik1`
    FOREIGN KEY (`KorisnikID`)
    REFERENCES `id11412390_webdip2017x141`.`Korisnik` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Racun_StatusNarudzbe1`
    FOREIGN KEY (`Status_narudžbeID`)
    REFERENCES `id11412390_webdip2017x141`.`StatusNarudzbe` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Racun_Restoran1`
    FOREIGN KEY (`RestoranID`)
    REFERENCES `id11412390_webdip2017x141`.`Restoran` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Racun_PovratnaInformacija2`
    FOREIGN KEY (`Povratna_informacijaID`)
    REFERENCES `id11412390_webdip2017x141`.`PovratnaInformacija` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`StavkeRacuna`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`StavkeRacuna` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Kolicina` INT NOT NULL,
  `Artikl_ID` INT NOT NULL,
  `Racun_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_StavkeRacuna_Artikl1_idx` (`Artikl_ID` ASC),
  INDEX `fk_StavkeRacuna_Racun1_idx` (`Racun_ID` ASC),
  CONSTRAINT `fk_StavkeRacuna_Artikl1`
    FOREIGN KEY (`Artikl_ID`)
    REFERENCES `id11412390_webdip2017x141`.`Artikl` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_StavkeRacuna_Racun1`
    FOREIGN KEY (`Racun_ID`)
    REFERENCES `id11412390_webdip2017x141`.`Racun` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `id11412390_webdip2017x141`.`Nagrada`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `id11412390_webdip2017x141`.`Nagrada` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `Popust` INT NOT NULL,
  `BrojBodova` INT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
