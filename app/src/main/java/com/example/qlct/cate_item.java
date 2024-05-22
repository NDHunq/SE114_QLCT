package com.example.qlct;

public class cate_item {


        private String imagecate;
        private String name;
        private int imagedelete;

        public cate_item(String imagecate, String name, int imagedelete) {
            this.imagecate = imagecate;
            this.name = name;
            this.imagedelete = imagedelete;
        }

        public String getImagecate() {
            return imagecate;
        }

        public void setImagecate(String imagecate) {
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


