package com.iunctainc.iuncta.app.util.databinding;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.iunctainc.iuncta.app.di.module.GlideApp;
import com.iunctainc.iuncta.app.di.module.GlideRequest;

public class ImageViewBindingUtils {
 /*   @BindingAdapter(value = {"image_url"}, requireAll = false)
    public static void setImageUrl(final ImageView imageView, String imageUrl) {
        GlideApp.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView);
    }*/

    @BindingAdapter(value = {"image_url", "resize", "placeholder"}, requireAll = false)
    public static void setImageUrl(final ImageView imageView, String image_url, boolean resize, Drawable placeholder) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load(image_url);
        if (resize)
            requests.override(120, 100);
        if (placeholder != null)
            requests.placeholder(placeholder);
        requests.centerCrop();
        requests.diskCacheStrategy(DiskCacheStrategy.DATA);
        requests.into(imageView);
    }

    @BindingAdapter(value = {"load_icon", "placeholder"}, requireAll = false)
    public static void loadIcon(final ImageView imageView, String image_url, Drawable placeholder) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load(image_url);
        if (placeholder != null)
            requests.placeholder(placeholder);
        requests.into(imageView);
    }

    @BindingAdapter(value = {"rectangle", "view_width", "view_height", "placeholder"}, requireAll = false)
    public static void rectangle(final ImageView imageView, String image_url, Integer view_width, Integer view_height, Drawable placeholder) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        if (view_width != null && view_height != null)
            options.override(view_width, view_height);
        if (placeholder != null)
            options.placeholder(placeholder);
        GlideApp.with(imageView.getContext()).load(image_url).apply(options).into(imageView);
    }


    @BindingAdapter(value = {"square", "placeholder"}, requireAll = false)
    public static void setSqure(final ImageView imageView, String image_url, Drawable placeholder) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load(image_url);
        requests.override(120);
        if (placeholder != null)
            requests.placeholder(placeholder);
        requests.centerCrop();
        requests.into(imageView);
    }

    @BindingAdapter(value = {"view_image"})
    public static void setImageUrl(final ImageView imageView, String image_url) {
        Glide.with(imageView.getContext()).load(image_url).centerCrop().apply(new RequestOptions()).into(imageView);

    }




/*
    @BindingAdapter(value = {"profile_url"}, requireAll = false)
    public static void setProfileUrl(final ImageView imageView, String image_url) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load("" + image_url);
        requests.placeholder(R.drawable.ic_dummy_profile);
        requests.circleCrop();
        requests.override(150);
        requests.into(imageView);
    }*/


    @BindingAdapter(value = {"simpleResourse"})
    public static void setStepGroupIcon(final ImageView imageView, int simpleResourse) {

        if (simpleResourse != -1) {
            imageView.setImageResource(simpleResourse);

        }
    }




  /*  @BindingAdapter(value = {"request_status", "is_read"})
    public static void setMeetIcon(final ImageView imageView, Integer status, Integer read) {

        if (status == null || read == null) {
            imageView.setVisibility(View.GONE);
            return;
        }
        imageView.setVisibility(View.VISIBLE);
        if (status == 1) {
            imageView.setImageResource(R.drawable.ic_two_check);
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.green), PorterDuff.Mode.SRC_IN);
        } else if (read == 1) {
            imageView.setImageResource(R.drawable.ic_two_check);
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.dim_black), PorterDuff.Mode.SRC_IN);
        } else {
            imageView.setImageResource(R.drawable.ic_one_check);
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.dim_black), PorterDuff.Mode.SRC_IN);
        }

    }
*/

    @BindingAdapter(value = {"chat_thumbnil", "corner"}, requireAll = false)
    public static void setThumnilUrl(final ImageView imageView, String image_url, String message) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load(image_url);
        requests.centerCrop();
     /*   if (message != null && message.length() > 0)
            requests.transform(new RoundedCornersTransformation(AppUtils.convertDpToPixel(20), AppUtils.convertDpToPixel(5), RoundedCornersTransformation.CornerType.TOP));
        else
            requests.transform(new RoundedCornersTransformation(AppUtils.convertDpToPixel(20), AppUtils.convertDpToPixel(5)));*/
        requests.into(imageView);
    }

}
