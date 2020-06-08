
<?php


 class Korisnik
{
    private $ime;
    private $prezime;
    private $username;
    private $lozinka;
    private $OIB;
    private $email;
    private $adresa;
    private $mobitel;
    private $brojPokusaja;
    private $status;
    private $aktivacijskiKod;
    private $brojBodova;
    private $tip_korisnika;

    /**
     * Korisnik constructor.
     */
    public function __construct()
    {
    }

    /**
     * @return string
     */
    public function getIme()
    {
        return $this->ime;
    }

    /**
     * @param string $ime
     */
    public function setIme($ime)
    {
        $this->ime = $ime;
    }

    /**
     * @return string
     */
    public function getPrezime()
    {
        return $this->prezime;
    }

    /**
     * @param string $prezime
     */
    public function setPrezime($prezime)
    {
        $this->prezime = $prezime;
    }

    /**
     * @return string
     */
    public function getUsername()
    {
        return $this->username;
    }

    /**
     * @param string $username
     */
    public function setUsername($username)
    {
        $this->username = $username;
    }

    /**
     * @return string
     */
    public function getLozinka()
    {
        return $this->lozinka;
    }

    /**
     * @param string $lozinka
     */
    public function setLozinka($lozinka)
    {
        $this->lozinka = $lozinka;
    }

    /**
     * @return integer
     */
    public function getOIB()
    {
        return $this->OIB;
    }

    /**
     * @param integer $OIB
     */
    public function setOIB($OIB)
    {
        $this->OIB = $OIB;
    }

    /**
     * @return string
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * @param string $email
     */
    public function setEmail($email)
    {
        $this->email = $email;
    }

    /**
     * @return string
     */
    public function getAdresa()
    {
        return $this->adresa;
    }

    /**
     * @param string $adresa
     */
    public function setAdresa($adresa)
    {
        $this->adresa = $adresa;
    }

    /**
     * @return string
     */
    public function getMobitel()
    {
        return $this->mobitel;
    }

    /**
     * @param string $mobitel
     */
    public function setMobitel($mobitel)
    {
        $this->mobitel = $mobitel;
    }

    /**
     * @return integer
     */
    public function getBrojPokusaja()
    {
        return $this->brojPokusaja;
    }

    /**
     * @param integer $brojPokusaja
     */
    public function setBrojPokusaja($brojPokusaja)
    {
        $this->brojPokusaja = $brojPokusaja;
    }

    /**
     * @return bool
     */
    public function getStatus()
    {
        return $this->status;
    }

    /**
     * @param bool $status
     */
    public function setStatus($status)
    {
        $this->status = $status;
    }

    /**
     * @return string
     */
    public function getAktivacijskiKod()
    {
        return $this->aktivacijskiKod;
    }

    /**
     * @param string $aktivacijskiKod
     */
    public function setAktivacijskiKod($aktivacijskiKod)
    {
        $this->aktivacijskiKod = $aktivacijskiKod;
    }

    /**
     * @return integer
     */
    public function getBrojBodova()
    {
        return $this->brojBodova;
    }

    /**
     * @param integer $brojBodova
     */
    public function setBrojBodova($brojBodova)
    {
        $this->brojBodova = $brojBodova;
    }

    /**
     * @return integer
     */
    public function getTipKorisnika()
    {
        return $this->tip_korisnika;
    }

    /**
     * @param mixed $tip_korisnika
     */
    public function setTipKorisnika($tip_korisnika)
    {
        $this->tip_korisnika = $tip_korisnika;
    }

}