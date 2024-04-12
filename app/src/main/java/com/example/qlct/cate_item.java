package com.example.qlct;

public class cate_item {


        private int imagecate;
        private String name;
        private int imagedelete;

        public cate_item(int imagecate, String name, int imagedelete) {
            this.imagecate = imagecate;
            this.name = name;
            this.imagedelete = imagedelete;
        }

        public int getImagecate() {
            return imagecate;
        }

        public void setImagecate(int imagecate) {
            this.imagecate = imagecate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImagedelete() {
            return imagedelete;
        }

        public void setImagedelete(int imagemore) {
            this.imagedelete = imagemore;
        }
    }


