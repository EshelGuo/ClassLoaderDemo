package com.eshel.classloaderdemo;

import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxJavaTest {

    public static void main(String args[]){
        File dir = new File("D://");
        File[] folders = dir.listFiles();
        Disposable subscribe = Observable.fromArray(folders)
                .flatMap(new Function<File, ObservableSource<File>>() {

                    @Override
                    public ObservableSource<File> apply(File file) throws Exception {
                        return Observable.fromArray(file.listFiles());
                    }
                })
                .filter(new Predicate<File>() {
                    @Override
                    public boolean test(File file) throws Exception {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Function<File, Bitmap>() {
                    @Override
                    public Bitmap apply(File file) throws Exception {
                        return getBitmapFromFile(file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Bitmap>() {

                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
//                        imageCollectorView.addImage(bitmap);
                    }
                });
        subscribe.dispose();
    }

    private static Bitmap getBitmapFromFile(File file) {
        return null;
    }
}
