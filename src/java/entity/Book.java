/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;


/**
 *
 * @author user
 */
@Entity
public class Book implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private int year;
    private String cover;
    //Чтобы загружать большие объемы данных, необходимо настроить сервер приложений и базу данных.
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] textBookInBytes;
    
    public Book() {
    }

    public Book(String title, String author, int year, String cover, byte[] textBookInBytes) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.cover = cover;
        this.textBookInBytes = textBookInBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public byte[] getTextBookInBytes() {
        return textBookInBytes;
    }

    public void setTextBookInBytes(byte[] textBookInBytes) {
        this.textBookInBytes = textBookInBytes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.title);
        hash = 89 * hash + Objects.hashCode(this.author);
        hash = 89 * hash + this.year;
        hash = 89 * hash + Objects.hashCode(this.cover);
        hash = 89 * hash + Arrays.hashCode(this.textBookInBytes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.cover, other.cover)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Arrays.equals(this.textBookInBytes, other.textBookInBytes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title=" + title + ", author=" + author + ", year=" + year + '}';
    }

   
    
    
}
