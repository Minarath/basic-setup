package com.iunctainc.iuncta.app.util.misc;

import android.text.format.DateFormat;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private final String TAG= RxBus.class.getSimpleName();
    public RxBus() {
    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
        Log.d(TAG, "send message- Time: "+DateFormat.format("MM/dd/yy h:mm:ss aa", System.currentTimeMillis())+ " Event type: "+ o.toString());
    }

    public Observable<Object> toObserverable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }


    //TODD to listen RXbus events - copy this code in your file

//    void setRxBus() {
//        compositeDisposable.add(rxBus.toObserverable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        if (o instanceof BusBean) {
//                            BusBean busBean = (BusBean) o;
//                            if (busBean.getBusId() == WelcomeActivity.BUS_SKIP) {
//                                obr_call_skip.setValue(busBean.getMessage());
//                            }
//                        }
//                    }
//                }));
//    }


}
