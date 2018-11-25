package com.example.rizki.JakarteKite.presenter;

public class Foto {
    private int id;
    private String name;
    private String harga;
    private String ket;
    private byte[] image;

    public Foto(int id, String name, String harga, String ket, byte[] image) {
        this.id = id;
        this.name = name;
        this.harga = harga;
        this.ket = ket;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
