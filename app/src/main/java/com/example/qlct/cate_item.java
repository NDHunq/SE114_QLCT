package com.example.qlct;

public class cate_item {


        private int imagecate;
        private String name;
        private int imagemore;

        public cate_item(int imagecate, String name, int imagemore) {
            this.imagecate = imagecate;
            this.name = name;
            this.imagemore = imagemore;
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

        public int getImagemore() {
            return imagemore;
        }

        public void setImagemore(int imagemore) {
            this.imagemore = imagemore;
        }
    }


